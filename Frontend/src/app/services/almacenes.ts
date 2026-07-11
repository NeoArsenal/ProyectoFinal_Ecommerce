import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AlmacenDTO {
  id: number;
  nombre: string;
  ubicacion: string;
}

@Injectable({ providedIn: 'root' })
export class AlmacenesService {
  private url = environment.apiUrl + '/almacenes';

  constructor(private http: HttpClient) {}

  listar(): Observable<AlmacenDTO[]> {
    return this.http.get<AlmacenDTO[]>(this.url);
  }

  crear(almacen: any): Observable<any> {
    return this.http.post(this.url, almacen, { responseType: 'text' });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}

