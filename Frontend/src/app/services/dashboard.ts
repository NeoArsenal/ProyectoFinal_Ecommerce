import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Definimos qué datos esperamos recibir del Backend
export interface DashboardDTO {
  totalVentas: number;
  cantidadPedidos: number;
  productosBajoStock: number;
}

@Injectable({ providedIn: 'root' })
export class DashboardService {
  // Asegúrate de que el puerto sea 8085 (o el que estés usando en Spring Boot)
  private url = 'http://localhost:8085/dashboard/resumen';

  constructor(private http: HttpClient) {}

  obtenerResumen(): Observable<DashboardDTO> {
    return this.http.get<DashboardDTO>(this.url);
  }
}
