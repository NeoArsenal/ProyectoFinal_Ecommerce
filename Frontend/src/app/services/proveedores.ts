import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Estructura de datos (igual al DTO de Java)
export interface ProveedorDTO {
  id: number;
  empresa: string;
  contacto: string;
  telefono: string;
}

// Estructura para crear (sin ID)
export type ProveedorPayload = Omit<ProveedorDTO, 'id'>;

@Injectable({ providedIn: 'root' })
export class ProveedoresService {
  private readonly url = environment.apiUrl + '/proveedores';

  constructor(private http: HttpClient) {}

  listar(): Observable<ProveedorDTO[]> {
    return this.http.get<ProveedorDTO[]>(this.url);
  }

  buscar(id: number): Observable<ProveedorDTO> {
    return this.http.get<ProveedorDTO>(`${this.url}/${id}`);
  }

  insertar(prov: ProveedorPayload): Observable<any> {
    return this.http.post(this.url, prov);
  }

  modificar(id: number, prov: ProveedorPayload): Observable<any> {
    return this.http.put(`${this.url}/${id}`, prov);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}

