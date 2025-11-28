import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UsuariosService {
  private url = 'http://localhost:8085/usuarios';

  constructor(private http: HttpClient) {}

  listar(): Observable<any[]> {
    return this.http.get<any[]>(this.url);
  }

  asignarRoles(id: number, rolesIds: number[]): Observable<any> {
    // Usamos 'text' para que Angular no falle si el backend devuelve texto plano
    return this.http.post(`${this.url}/${id}/roles`, rolesIds, { responseType: 'text' });
  }

  // --- ESTE ES EL MÉTODO QUE TE FALTABA ---
  obtenerRolesActuales(id: number): Observable<number[]> {
    // Esperamos un array de números (IDs) [1, 2]
    return this.http.get<number[]>(`${this.url}/${id}/roles`);
  }
}