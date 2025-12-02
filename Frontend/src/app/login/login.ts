import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {
  credenciales = { email: '', password: '' };
  error = '';

  constructor(private auth: AuthService, private router: Router) {}

  ingresar() {
    // Validación básica antes de enviar
    if (!this.credenciales.email || !this.credenciales.password) {
      this.error = "Complete todos los campos";
      return;
    }

    this.auth.login(this.credenciales).subscribe({
      next: (usuario) => {
        // Redirige al Home
        this.router.navigate(['/home']);
      },
      error: (err) => {
        // Si el backend falla (400 Bad Request), mostramos el mensaje
        this.error = err.error || 'Credenciales incorrectas o error de servidor';
      }
    });
  }
}
