import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface InventarioDTO {
  id: number;
  cantidad: number;
  productoId: number;
  productoNombre: string;
  almacenId: number;
  almacenNombre: string;
}


export interface InventarioPayload {
  productoId: number;
  almacenId: number;
  cantidad: number;
}

@Injectable({ providedIn: 'root' })
export class InventariosService {
  private url = 'http://localhost:8085/inventarios';

  constructor(private http: HttpClient) {}

  listar(): Observable<InventarioDTO[]> {
    return this.http.get<InventarioDTO[]>(this.url);
  }

  guardar(inv: InventarioPayload): Observable<any> {
    // responseType: 'text' porque el backend devuelve un String
    return this.http.post(this.url, inv, { responseType: 'text' });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}