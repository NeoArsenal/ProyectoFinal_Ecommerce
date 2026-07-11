import { environment } from 'src/environments/environment';
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
  private url = environment.apiUrl + '/dashboard';

  constructor(private http: HttpClient) {}

  obtenerResumen(): Observable<DashboardDTO> {
    return this.http.get<DashboardDTO>(`${this.url}/resumen`);
  }

  obtenerTendencia(): Observable<number[]> {
    return this.http.get<number[]>(`${this.url}/tendencia`);
  }

  obtenerActividades(): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/actividades`);
  }
}

