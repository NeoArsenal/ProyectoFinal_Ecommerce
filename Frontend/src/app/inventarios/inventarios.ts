import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { InventariosService, InventarioDTO, InventarioPayload } from '../services/inventarios';
import { ProductosService, ProductoDTO } from '../productos';
import { AlmacenesService, AlmacenDTO } from '../services/almacenes';

@Component({
  selector: 'app-inventarios',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './inventarios.html',
  styleUrls: ['./inventarios.css']
})
export class Inventarios implements OnInit {
  
  listaInventario: InventarioDTO[] = [];
  listaProductos: ProductoDTO[] = [];
  listaAlmacenes: AlmacenDTO[] = [];
  
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private invService: InventariosService,
    private prodService: ProductosService,
    private almService: AlmacenesService
  ) {
    this.form = this.fb.group({
      productoId: [null, Validators.required],
      almacenId: [null, Validators.required],
      cantidad: [null, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.cargarTodo();
  }

  cargarTodo() {
    // Cargar combos
    this.prodService.listar().subscribe(data => this.listaProductos = data);
    this.almService.listar().subscribe(data => this.listaAlmacenes = data);
    
    // Cargar tabla principal
    this.invService.listar().subscribe(data => this.listaInventario = data);
  }

  guardar() {
    if (this.form.invalid) return;

    const payload: InventarioPayload = {
      productoId: Number(this.form.value.productoId),
      almacenId: Number(this.form.value.almacenId),
      cantidad: this.form.value.cantidad
    };

    this.invService.guardar(payload).subscribe({
      next: (resp) => {
        alert(resp); // "Inventario actualizado"
        this.cargarTodo();
        this.form.reset();
      },
      error: (err) => console.error(err)
    });
  }

  borrar(id: number) {
    if(confirm('¿Eliminar registro de inventario?')) {
      this.invService.eliminar(id).subscribe(() => this.cargarTodo());
    }
  }
}