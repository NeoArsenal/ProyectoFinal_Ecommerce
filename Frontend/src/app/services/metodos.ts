import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MetodoDTO {
  id: number;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class MetodosService {
  private url = 'http://localhost:8085/metodos-envio';

  constructor(private http: HttpClient) {}

  listar(): Observable<MetodoDTO[]> {
    return this.http.get<MetodoDTO[]>(this.url);
  }

  guardar(metodo: any): Observable<any> {
    return this.http.post(this.url, metodo, { responseType: 'text' });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
