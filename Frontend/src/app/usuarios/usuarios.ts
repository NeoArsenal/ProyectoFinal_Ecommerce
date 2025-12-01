import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { UsuariosService } from '../services/usuarios';
import { RolesService, RolDTO } from '../services/roles';
import { AuthService } from '../services/auth';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.html',
  // CORRECCIÓN: Ahora apuntamos al CSS propio de este componente
  styleUrls: ['./usuarios.css'] 
})
export class Usuarios implements OnInit {
  
  usuarios: any[] = [];
  rolesDisponibles: RolDTO[] = [];
  
  usuarioSeleccionado: any = null;
  rolesSeleccionados: number[] = [];

  constructor(
    private userService: UsuariosService,
    private rolesService: RolesService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.userService.listar().subscribe(u => this.usuarios = u);
    this.rolesService.listar().subscribe(r => this.rolesDisponibles = r);
  }

  seleccionar(u: any) {
    if(!this.canManageUsers()) return; // evita abrir editor si no es admin
    this.usuarioSeleccionado = u;
    this.rolesSeleccionados = []; 

    this.userService.obtenerRolesActuales(u.id).subscribe({
      next: (ids) => {
        this.rolesSeleccionados = ids; 
        console.log('Roles actuales:', ids);
      },
      error: (err) => console.error('Error al cargar roles', err)
    });
  }

  guardarRoles() {
    if(!this.usuarioSeleccionado) return;
    if(!this.canManageUsers()) return;

    this.userService.asignarRoles(this.usuarioSeleccionado.id, this.rolesSeleccionados)
      .subscribe({
        next: (respuesta) => {
          console.log('Respuesta Backend:', respuesta);
          alert('¡Roles asignados correctamente!');
          this.usuarioSeleccionado = null; 
          this.cargar(); 
        },
        error: (err) => {
          console.error('Error al asignar roles:', err);
          alert('Error al guardar. Revisa la consola (F12).');
        }
      });
  }

  // Sólo administradores pueden gestionar roles de usuarios
  canManageUsers(): boolean {
    return this.auth.isAdmin();
  }
}