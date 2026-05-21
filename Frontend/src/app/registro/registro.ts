import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './registro.html',
  styleUrls: ['./registro.css']
})
export class Registro {

  // Objeto con TODOS los campos que el backend espera
  datos = {
    nombre:   '',
    apellido: '',
    dni:      '',
    genero:   '',
    email:    '',
    password: ''
  };

  mensajeError = '';
  mensajeExito = '';
  cargando     = false;

  constructor(private auth: AuthService, private router: Router) {}

  // Solo permite dígitos en el campo DNI
  soloNumeros(event: KeyboardEvent): boolean {
    return /^\d$/.test(event.key);
  }

  // Solo permite letras (incluyendo tildes, ñ) y espacios para nombre/apellido
  // Bloquea: números, !"#$%&/()=? y cualquier símbolo
  soloLetras(event: KeyboardEvent): boolean {
    return /^[a-zA-Z\u00C0-\u024F ]$/.test(event.key);
  }

  crearCuenta() {
    this.mensajeError = '';
    this.mensajeExito = '';
    this.cargando     = true;

    this.auth.registrar(this.datos).subscribe({
      next: () => {
        this.cargando     = false;
        this.mensajeExito = '¡Cuenta creada con éxito! Redirigiendo al login...';
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err) => {
        this.cargando = false;

        // Spring Boot puede devolver dos tipos de error:
        // 1. RuntimeException (string) → "El DNI ya está registrado"
        // 2. @Valid failure (objeto JSON) → { errors: [...] }
        if (typeof err.error === 'string') {
          this.mensajeError = err.error;
        } else if (err.error?.errors) {
          // Tomamos el primer mensaje de validación del @Valid
          this.mensajeError = err.error.errors[0]?.defaultMessage || 'Error de validación';
        } else {
          this.mensajeError = 'Error al registrar usuario. Intenta nuevamente.';
        }
      }
    });
  }
}