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
    this.auth.login(this.credenciales).subscribe({
      next: (usuario) => {
        alert(`¡Bienvenido ${usuario.email}!`);
        this.router.navigate(['/home']); // Redirigir al inicio
      },
      error: () => {
        this.error = 'Credenciales incorrectas. Intente de nuevo.';
      }
    });
  }
}
