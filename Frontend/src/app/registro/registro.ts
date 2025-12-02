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
  styleUrls: ['../login/login.css'] // Reusamos CSS del login
})
export class Registro {
  datos = { email: '', password: '' };
  mensajeError = '';

  constructor(private auth: AuthService, private router: Router) {}

  crearCuenta() {
    this.auth.registrar(this.datos).subscribe({
      next: () => {
        alert('¡Cuenta creada! Por favor inicia sesión.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        // Captura el error del backend (ej: "Email inválido")
        this.mensajeError = err.error || 'Error al registrar usuario';
      }
    });
  }
}