import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductosService, ProductoDTO, ProductoPayload, Categoria } from '../productos';
// Importamos el servicio de proveedores para llenar el select
import { ProveedoresService, ProveedorDTO } from '../services/proveedores';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './productos.html',
  styleUrls: ['./productos.css']
})
export class Productos implements OnInit {
  
  productos: ProductoDTO[] = [];
  categorias: Categoria[] = []; // Lista para el combo de categorías
  proveedores: ProveedorDTO[] = []; // Lista para el select múltiple
  form: FormGroup;

  constructor(
    private fb: FormBuilder, 
    private prodService: ProductosService, // Corregido el nombre para consistencia
    private provService: ProveedoresService
  ) {
    this.form = this.fb.group({
      id: [0],
      nombre: ['', [Validators.required, Validators.maxLength(50)]],
      descripcion: ['', [Validators.required, Validators.maxLength(100)]],
      precio: [null, [Validators.required, Validators.min(0.01)]],
      stock: [0, [Validators.required, Validators.min(0)]],
      categoriaId: [null, [Validators.required]],
      proveedorIds: [[]] // Inicializamos como array vacío
    });
  }

  ngOnInit(): void {
    this.cargarTodo();
  }

  // Getters para validaciones en el HTML
  get nombreCtl() { return this.form.get('nombre'); }
  get descCtl() { return this.form.get('descripcion'); }
  get precioCtl() { return this.form.get('precio'); }
  get catCtl() { return this.form.get('categoriaId'); }

  get deshabilitarAgregar(): boolean {
    const id = Number(this.form.get('id')?.value || 0);
    return !this.form.valid || id > 0;
  }

  get deshabilitarModificar(): boolean {
    const id = Number(this.form.get('id')?.value || 0);
    return !this.form.valid || id <= 0;
  }

  cargarTodo() {
    this.prodService.listar().subscribe((d: ProductoDTO[]) => {
      this.productos = d;
    });
    this.prodService.listarCategorias().subscribe((c: Categoria[]) => {
      this.categorias = c;
    });
    // Cargar proveedores disponibles
    this.provService.listar().subscribe(p => this.proveedores = p);
  }

  alta() {
    if (this.form.invalid) return;
    this.guardar();
  }

  modificacion() {
    if (this.form.invalid) return;
    this.guardar();
  }

  // Método unificado para guardar (sirve para alta y modificación gracias al backend)
  guardar() {
    // 1. Armamos el objeto con los datos del formulario
    const payload: ProductoPayload = {
      id: this.form.value.id,
      nombre: this.form.value.nombre,
      descripcion: this.form.value.descripcion,
      precio: this.form.value.precio,
      stock: this.form.value.stock,
      categoriaId: Number(this.form.value.categoriaId),
      proveedorIds: this.form.value.proveedorIds // Envía el array de IDs seleccionados
    };

    // 2. DECISIÓN INTELIGENTE: ¿Es nuevo o es edición?
    if (payload.id && payload.id > 0) {
      
      // --- CASO EDITAR (PUT) ---
      this.prodService.modificar(payload).subscribe({
        next: () => {
          this.cargarTodo();
          this.resetForm();
          alert("¡Producto actualizado correctamente!");
        },
        error: (err) => {
          console.error("Error al actualizar:", err);
          alert("Error al actualizar. Revisa la consola.");
        }
      });

    } else {
      
      // --- CASO CREAR (POST) ---
      this.prodService.insertar(payload).subscribe({
        next: () => {
          this.cargarTodo();
          this.resetForm();
          alert("¡Producto creado correctamente!");
        },
        error: (err) => {
          console.error("Error al crear:", err);
          alert("Error al crear. Revisa la consola.");
        }
      });
    }
  }

  baja(id: number) {
    if(confirm("¿Estás seguro de borrar este producto?")) {
      this.prodService.eliminar(id).subscribe(() => this.cargarTodo());
    }
  }

  seleccionar(p: ProductoDTO) {
    this.form.patchValue({
        id: p.id,
        nombre: p.nombre,
        descripcion: p.descripcion,
        precio: p.precio,
        stock: p.stock,
        categoriaId: p.categoriaId,
        proveedorIds: p.proveedorIds // Esto marca automáticamente los proveedores en el select múltiple
    });
  }

  resetForm() {
    this.form.reset({ id: 0, nombre: '', descripcion: '', precio: null, stock: 0, categoriaId: null, proveedorIds: [] });
  }
}