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

  constructor(public auth: AuthService, private router: Router) {}

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
