import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HistorialService, HistorialDTO } from '../services/historial';

@Component({
  selector: 'app-historial',
  standalone: true,
  imports: [CommonModule], // Importamos CommonModule para usar *ngFor y Pipes
  templateUrl: './historial.html',
  styleUrls: ['./historial.css']
})
export class Historial implements OnInit {
  
  pedidos: HistorialDTO[] = [];

  constructor(private historialService: HistorialService) {}

  ngOnInit(): void {
    this.cargarHistorial();
  }

  cargarHistorial() {
    this.historialService.listarHistorial().subscribe({
      next: (data) => {
        this.pedidos = data;
        console.log('Historial cargado:', data);
      },
      error: (err) => console.error('Error al cargar historial', err)
    });
  }
}
