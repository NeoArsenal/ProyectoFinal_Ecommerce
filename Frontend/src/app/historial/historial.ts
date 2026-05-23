import { Component, OnInit } from '@angular/core';
import { CommonModule }       from '@angular/common';
import { AuthService }        from '../services/auth';
import { HistorialService, HistorialDTO } from '../services/historial';

@Component({
  selector:    'app-historial',
  standalone:  true,
  imports:     [CommonModule],
  templateUrl: './historial.html',
  styleUrls:   ['./historial.css']
})
export class Historial implements OnInit {

  pedidos:      HistorialDTO[] = [];
  cargando:     boolean = true;
  mensajeError: string  = '';

  // Email del usuario logueado para filtrar visualmente
  get emailUsuario(): string {
    return this.auth.usuario?.email ?? '';
  }

  constructor(
    private historialService: HistorialService,
    private auth:             AuthService
  ) {}

  ngOnInit(): void {
    this.historialService.listarHistorial().subscribe({
      next:  (data) => { this.pedidos = data; this.cargando = false; },
      error: ()     => { this.mensajeError = 'No se pudo cargar el historial.'; this.cargando = false; }
    });
  }

  badgeClase(estado: string): string {
    if (estado === 'PENDIENTE')  return 'badge-pendiente';
    if (estado === 'ENVIADO')    return 'badge-enviado';
    if (estado === 'ENTREGADO')  return 'badge-entregado';
    return 'badge-default';
  }
}
