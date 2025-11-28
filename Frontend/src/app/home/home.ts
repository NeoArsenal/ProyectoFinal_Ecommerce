import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
// Importamos el servicio y la interfaz que acabamos de crear
import { DashboardService, DashboardDTO } from '../services/dashboard';

@Component({
  selector: 'app-home',
  imports: [CommonModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {

  // Variable para guardar los datos. Inicia en null.
  stats: DashboardDTO | null = null;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.cargarEstadisticas();
  }

  cargarEstadisticas() {
    this.dashboardService.obtenerResumen().subscribe({
      next: (data) => {
        this.stats = data;
        console.log('Datos del dashboard recibidos:', data);
      },
      error: (err) => {
        console.error('Error al cargar dashboard:', err);
        // Opcional: poner datos falsos si falla para que no se vea vacío
        // this.stats = { totalVentas: 0, cantidadPedidos: 0, productosBajoStock: 0 };
      }
    });
  }

}
