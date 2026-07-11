import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EnvioDTO {
  id: number;
  tracking: string;
  estado: string; // "PENDIENTE", "ENVIADO", "ENTREGADO"
  metodo: string;
  cliente: string;
  direccion: string;
  pedidoId: number;
}

@Injectable({ providedIn: 'root' })
export class EnviosService {
  private url = environment.apiUrl + '/envios';

  constructor(private http: HttpClient) {}

  listar(): Observable<EnvioDTO[]> {
    return this.http.get<EnvioDTO[]>(this.url);
  }

  // Método para cambiar el estado (Usamos PATCH porque solo actualizamos un campo)
  actualizarEstado(id: number, nuevoEstado: string): Observable<any> {
    return this.http.patch(`${this.url}/${id}/estado?estado=${nuevoEstado}`, {});
  }
}

