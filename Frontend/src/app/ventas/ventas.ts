import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// 1. IMPORTAR PRODUCTOS
// ".." significa salir de la carpeta 'ventas' hacia 'app'
// Buscamos 'productos.service.ts' que está en 'src/app/'
import { ProductosService, ProductoDTO } from '../productos'; 

// 2. IMPORTAR VENTAS (Servicio)
// ".." salimos de 'ventas'
// "services" entramos a la carpeta servicios
// Buscamos 'ventas.service.ts'
import { VentasService, ItemVenta, VentaPayload } from '../services/ventas';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-ventas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],
  // Asegúrate que estos nombres coincidan con tus archivos reales en la carpeta 'ventas'
  templateUrl: './ventas.html', 
  styleUrls: ['./ventas.css']   
})
export class Ventas implements OnInit {
  
  listaProductos: ProductoDTO[] = [];
  
  productoSeleccionadoId: number | null = null;
  cantidad: number = 1;
  usuarioId: number = 1; // ID fijo del usuario SQL

  carrito: ItemVenta[] = [];
  totalVenta: number = 0.0;

  constructor(
    private productosService: ProductosService,
    private ventasService: VentasService
  ) {}

  ngOnInit(): void {
    // Cargar productos al iniciar
    this.productosService.listar().subscribe(data => {
      this.listaProductos = data;
    });
  }

  agregarAlCarrito() {
    if (!this.productoSeleccionadoId || this.cantidad <= 0) {
      alert("Seleccione un producto y una cantidad válida");
      return;
    }

    // Buscamos el producto en la lista cargada
    const productoReal = this.listaProductos.find(p => p.id == this.productoSeleccionadoId);

    if (productoReal) {
      // Verificar si ya existe en el carrito
      const itemExistente = this.carrito.find(item => item.productoId == productoReal.id);

      if (itemExistente) {
        itemExistente.cantidad += this.cantidad;
        itemExistente.subtotal = itemExistente.cantidad * itemExistente.precio;
      } else {
        // Crear nuevo item
        const nuevoItem: ItemVenta = {
          productoId: productoReal.id,
          nombreProducto: productoReal.nombre,
          precio: productoReal.precio,
          cantidad: this.cantidad,
          subtotal: this.cantidad * productoReal.precio
        };
        this.carrito.push(nuevoItem);
      }

      this.calcularTotal();
      
      // Limpiar inputs
      this.productoSeleccionadoId = null;
      this.cantidad = 1;
    }
  }

  eliminarDelCarrito(index: number) {
    this.carrito.splice(index, 1);
    this.calcularTotal();
  }

  calcularTotal() {
    this.totalVenta = this.carrito.reduce((acc, item) => acc + (item.subtotal || 0), 0);
  }

  guardarVenta() {
    if (this.carrito.length === 0) return;

    if(!confirm(`¿Confirmar venta por S/ ${this.totalVenta}?`)) return;

    // Preparamos el objeto limpio para el Backend
    const ventaFinal: VentaPayload = {
      usuarioId: this.usuarioId,
      items: this.carrito.map(item => ({
        productoId: item.productoId,
        cantidad: item.cantidad,
        precio: item.precio
      }))
    };

    this.ventasService.registrarVenta(ventaFinal).subscribe({
      next: (respuesta) => {
        alert(respuesta); // Mensaje del Backend
        this.carrito = [];
        this.calcularTotal();
      },
      error: (err) => {
        console.error(err);
        alert("Error al guardar la venta.");
      }
    });
  }
}