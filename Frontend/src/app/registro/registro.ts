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

  constructor(private auth: AuthService, private router: Router) {}

  crearCuenta() {
    this.auth.registrar(this.datos).subscribe({
      next: () => {
        alert('Cuenta creada. Ahora inicia sesión.');
        this.router.navigate(['/login']);
      },
      error: (err) => alert('Error: ' + (err.error || 'No se pudo registrar'))
    });
  }
}