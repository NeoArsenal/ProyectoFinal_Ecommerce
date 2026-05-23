import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }  from '@angular/forms';
import { AuthService }  from '../services/auth';

@Component({
  selector:    'app-perfil',
  standalone:  true,
  imports:     [CommonModule, FormsModule],
  templateUrl: './perfil.html',
  styleUrls:   ['./perfil.css']
})
export class Perfil implements OnInit {

  perfil: any = {
    nombre:    '',
    apellido:  '',
    genero:    '',
    dni:       '',
    email:     '',
    telefono:  '',
    direccion: ''
  };

  usuarioId:   number  = 0;
  cargando:    boolean = false;
  guardando:   boolean = false;
  mensajeOk:   string  = '';
  mensajeError:string  = '';

  get generoTexto(): string {
    return this.perfil.genero === 'M' ? 'Masculino'
         : this.perfil.genero === 'F' ? 'Femenino'
         : 'No especificado';
  }

  constructor(private auth: AuthService) {
    this.usuarioId = this.auth.usuario?.id ?? 0;
  }

  ngOnInit(): void {
    if (this.usuarioId > 0) {
      this.cargando = true;
      this.auth.obtenerPerfil(this.usuarioId).subscribe({
        next: (data) => {
          this.perfil  = data;
          this.cargando = false;
        },
        error: () => {
          this.cargando = false;
          this.mensajeError = 'No se pudo cargar el perfil.';
        }
      });
    }
  }

  guardar(): void {
    this.mensajeOk    = '';
    this.mensajeError = '';
    this.guardando    = true;

    this.auth.guardarPerfil(this.usuarioId, this.perfil).subscribe({
      next: () => {
        this.guardando  = false;
        this.mensajeOk  = '✅ Perfil actualizado correctamente.';
        setTimeout(() => this.mensajeOk = '', 3500);
      },
      error: () => {
        this.guardando    = false;
        this.mensajeError = '❌ Error al guardar los cambios.';
      }
    });
  }
}
