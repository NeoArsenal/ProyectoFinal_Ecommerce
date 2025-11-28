import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MetodosService, MetodoDTO } from '../services/metodos';

@Component({
  selector: 'app-metodos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './metodos.html',
  styleUrls: ['../almacenes/almacenes.css'] // Reusamos estilos de almacenes
})
export class Metodos implements OnInit {
  metodos: MetodoDTO[] = [];
  nombreNuevo: string = '';

  constructor(private service: MetodosService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.service.listar().subscribe(data => this.metodos = data);
  }

  guardar() {
    if (!this.nombreNuevo.trim()) return;
    this.service.guardar({ nombre: this.nombreNuevo }).subscribe(() => {
      this.nombreNuevo = '';
      this.cargar();
    });
  }

  borrar(id: number) {
    if(confirm('¿Borrar método?')) {
      this.service.eliminar(id).subscribe(() => this.cargar());
    }
  }
}
