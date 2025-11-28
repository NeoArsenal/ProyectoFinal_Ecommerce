import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnviosService, EnvioDTO } from '../services/envios';

@Component({
  selector: 'app-envios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './envios.html',
  styleUrls: ['./envios.css']
})
export class Envios implements OnInit {
  
  envios: EnvioDTO[] = [];

  constructor(private service: EnviosService) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.service.listar().subscribe(data => this.envios = data);
  }

  cambiarEstado(envio: EnvioDTO, nuevoEstado: string) {
    if(!confirm(`¿Cambiar estado del tracking ${envio.tracking} a ${nuevoEstado}?`)) return;

    this.service.actualizarEstado(envio.id, nuevoEstado).subscribe({
      next: () => {
        this.cargar(); // Recargar para ver el cambio
      },
      error: (e) => alert('Error al actualizar envío')
    });
  }
}
