import { Component, OnInit } from '@angular/core';
import { CommonModule }      from '@angular/common';
import { FormsModule }       from '@angular/forms';
import { RouterOutlet }      from '@angular/router';

import { ProductosService, ProductoDTO } from '../productos';
import { VentasService, ItemVenta, VentaPayload, MetodoEnvio } from '../services/ventas';
import { AuthService } from '../services/auth';

@Component({
  selector:    'app-ventas',
  standalone:  true,
  imports:     [CommonModule, FormsModule],
  templateUrl: './ventas.html',
  styleUrls:   ['./ventas.css']
})
export class Ventas implements OnInit {

  // --- Datos del formulario ---
  listaProductos:       ProductoDTO[]  = [];
  listaMetodosEnvio:    MetodoEnvio[]  = [];
  productoSeleccionadoId: number | null = null;
  cantidad: number = 1;

  // --- Sesión: tomamos el ID del usuario logueado ---
  get usuarioId(): number {
    return this.auth.usuario?.id ?? 1;
  }
  get usuarioEmail(): string {
    return this.auth.usuario?.email ?? '';
  }

  // --- Carrito ---
  carrito:     ItemVenta[] = [];
  totalVenta:  number = 0;

  // --- Pago y Envío ---
  metodoPago:    string = '';
  metodoEnvioId: number | null = null;

  // Opciones de pago hardcodeadas (no requieren endpoint)
  metodosPago = ['Efectivo', 'Tarjeta de Crédito', 'Yape', 'Plin'];

  // --- Estados UI ---
  paso: 'carrito' | 'pago' | 'confirmacion' = 'carrito';
  mensajeError = '';
  cargando = false;
  pedidoConfirmado: any = null; // Datos de la boleta

  constructor(
    private productosService: ProductosService,
    private ventasService:    VentasService,
    private auth:             AuthService
  ) {}

  ngOnInit(): void {
    this.productosService.listar().subscribe(data => {
      this.listaProductos = data;
    });
    this.ventasService.listarMetodosEnvio().subscribe(data => {
      this.listaMetodosEnvio = data;
    });
  }

  // --- Paso 1: Carrito ---

  agregarAlCarrito() {
    if (!this.productoSeleccionadoId || this.cantidad <= 0) {
      this.mensajeError = 'Seleccione un producto y una cantidad válida.';
      return;
    }
    this.mensajeError = '';

    const producto = this.listaProductos.find(p => p.id == this.productoSeleccionadoId);
    if (!producto) return;

    const existente = this.carrito.find(i => i.productoId == producto.id);
    if (existente) {
      existente.cantidad += this.cantidad;
      existente.subtotal  = existente.cantidad * existente.precio;
    } else {
      this.carrito.push({
        productoId:     producto.id,
        nombreProducto: producto.nombre,
        precio:         producto.precio,
        cantidad:       this.cantidad,
        subtotal:       this.cantidad * producto.precio
      });
    }

    this.calcularTotal();
    this.productoSeleccionadoId = null;
    this.cantidad = 1;
  }

  eliminarDelCarrito(i: number) {
    this.carrito.splice(i, 1);
    this.calcularTotal();
  }

  calcularTotal() {
    this.totalVenta = this.carrito.reduce((acc, item) => acc + (item.subtotal ?? 0), 0);
  }

  irAPago() {
    if (this.carrito.length === 0) {
      this.mensajeError = 'El carrito está vacío.';
      return;
    }
    this.mensajeError = '';
    this.paso = 'pago';
  }

  volverAlCarrito() {
    this.paso = 'carrito';
    this.mensajeError = '';
  }

  // --- Paso 2: Pago y Envío → Finalizar ---

  confirmarPedido() {
    if (!this.metodoPago) {
      this.mensajeError = 'Selecciona un método de pago.';
      return;
    }
    if (!this.metodoEnvioId) {
      this.mensajeError = 'Selecciona un método de envío.';
      return;
    }

    this.mensajeError = '';
    this.cargando = true;

    const payload: VentaPayload = {
      usuarioId:     this.usuarioId,
      metodoPago:    this.metodoPago,
      metodoEnvioId: this.metodoEnvioId,
      items: this.carrito.map(item => ({
        productoId: item.productoId,
        cantidad:   item.cantidad,
        precio:     item.precio
      }))
    };

    this.ventasService.registrarVenta(payload).subscribe({
      next: () => {
        this.cargando = false;
        // Guardamos datos de la boleta antes de limpiar
        this.pedidoConfirmado = {
          fecha:         new Date().toLocaleDateString('es-PE', { dateStyle: 'long' }),
          hora:          new Date().toLocaleTimeString('es-PE', { hour: '2-digit', minute: '2-digit' }),
          email:         this.usuarioEmail,
          items:         [...this.carrito],
          total:         this.totalVenta,
          metodoPago:    this.metodoPago,
          metodoEnvio:   this.listaMetodosEnvio.find(m => m.id == this.metodoEnvioId)?.nombre ?? ''
        };
        // Limpiar carrito y pasar a confirmación
        this.carrito = [];
        this.totalVenta = 0;
        this.paso = 'confirmacion';
      },
      error: (err) => {
        this.cargando = false;
        this.mensajeError = typeof err.error === 'string'
          ? err.error
          : 'Error al registrar la venta. Intenta nuevamente.';
      }
    });
  }

  nuevaVenta() {
    this.paso              = 'carrito';
    this.metodoPago        = '';
    this.metodoEnvioId     = null;
    this.pedidoConfirmado  = null;
    this.mensajeError      = '';
  }
}