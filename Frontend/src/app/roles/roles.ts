import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RolesService, RolDTO } from '../services/roles';

@Component({
  selector: 'app-roles',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './roles.html',
  styleUrls: ['../almacenes/almacenes.css'] // Reusa estilos
})
export class Roles implements OnInit {
  roles: RolDTO[] = [];
  nombreNuevo: string = '';

  constructor(private service: RolesService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.service.listar().subscribe(data => this.roles = data);
  }

  guardar() {
    if (!this.nombreNuevo.trim()) return;
    this.service.guardar({ nombre: this.nombreNuevo }).subscribe(() => {
      this.nombreNuevo = '';
      this.cargar();
    });
  }

  borrar(id: number) {
    if(confirm('¿Borrar rol?')) {
      this.service.eliminar(id).subscribe(() => this.cargar());
    }
  }
}
