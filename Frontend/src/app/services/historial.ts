import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// DTO que mapea lo que envía el Backend
export interface HistorialDTO {
  idPedido: number;
  fecha: string;
  total: number;
  emailUsuario: string;
  metodoPago: string;
  estadoEnvio: string;
  tracking: string;
}

@Injectable({ providedIn: 'root' })
export class HistorialService {
  // Asegúrate de que el puerto sea el correcto (8085)
  private readonly url = environment.apiUrl + '/pedidos/historial';

  constructor(private http: HttpClient) {}

  listarHistorial(): Observable<HistorialDTO[]> {
    return this.http.get<HistorialDTO[]>(this.url);
  }
}

