import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RolDTO {
  id: number;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class RolesService {
  private url = 'http://localhost:8085/roles';

  constructor(private http: HttpClient) {}

  listar(): Observable<RolDTO[]> {
    return this.http.get<RolDTO[]>(this.url);
  }

  guardar(rol: any): Observable<any> {
    return this.http.post(this.url, rol, { responseType: 'text' });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}