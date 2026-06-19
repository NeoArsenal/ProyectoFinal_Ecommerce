import { Component, signal } from '@angular/core';
import { RouterLink, RouterOutlet, NavigationEnd, Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth';
import { filter } from 'rxjs';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './app.html', // Ojo: Angular moderno usa app.component.html
  styleUrls: ['./app.css'],
})
export class App {

  rutaActual: string = '';
  modoDaltonico: boolean = false;

  // ── Control de Tamaño de Fuente — WCAG 2.1 Criterio 1.4.4 ──
  // 0 = pequeño (13px) | 1 = normal (16px) | 2 = grande (22px)
  nivelFuente: number = 1;
  private readonly tamanosFuente = ['13px', '16px', '22px'];

  constructor(public auth: AuthService, private router: Router) {}

  toggleDaltonismo() {
    this.modoDaltonico = !this.modoDaltonico;
    if (this.modoDaltonico) {
      document.body.classList.add('modo-daltonico');
    } else {
      document.body.classList.remove('modo-daltonico');
    }
  }

  // Reduce el tamaño de fuente un nivel (mínimo nivel 0)
  reducirFuente() {
    if (this.nivelFuente > 0) {
      this.nivelFuente--;
      this.aplicarFuente();
    }
  }

  // Aumenta el tamaño de fuente un nivel (máximo nivel 2)
  aumentarFuente() {
    if (this.nivelFuente < 2) {
      this.nivelFuente++;
      this.aplicarFuente();
    }
  }

  // Restaura el tamaño normal (16px)
  resetFuente() {
    this.nivelFuente = 1;
    this.aplicarFuente();
  }

  // Aplica el tamaño al elemento raíz — todos los rem del sistema escalan
  private aplicarFuente() {
    document.documentElement.style.fontSize = this.tamanosFuente[this.nivelFuente];
  }

  ngOnInit() {
    // Escuchamos cada vez que cambia la URL
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.rutaActual = event.urlAfterRedirects;
    });
  }

  // Helper para saber si es Home
  get esHome(): boolean {
    return this.rutaActual === '/home' || this.rutaActual === '/';
  }

  // Helper para ocultar el enlace si ya estamos ahí
  noEsRuta(ruta: string): boolean {
    return this.rutaActual !== ruta;
  }
}
