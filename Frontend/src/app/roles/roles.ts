import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RolesService, RolDTO } from '../services/roles';
import { AuthService } from '../services/auth';

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

  constructor(private service: RolesService, private auth: AuthService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.service.listar().subscribe(data => this.roles = data);
  }

  guardar() {
    if (!this.nombreNuevo.trim()) return;
    if (!this.canManageRoles()) return;
    this.service.guardar({ nombre: this.nombreNuevo }).subscribe(() => {
      this.nombreNuevo = '';
      this.cargar();
    });
  }

  borrar(id: number) {
    if(!this.canManageRoles()) return;
    if(confirm('¿Borrar rol?')) {
      this.service.eliminar(id).subscribe(() => this.cargar());
    }
  }

  // Sólo administradores pueden crear/borrar roles desde el frontend
  canManageRoles(): boolean {
    return this.auth.isAdmin();
  }
}
