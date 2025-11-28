import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CategoriaDTO {
  id: number;
  nombre: string;
  descripcion: string;
}

@Injectable({ providedIn: 'root' })
export class CategoriasService {
  private url = 'http://localhost:8085/categorias';

  constructor(private http: HttpClient) {}

  listar(): Observable<CategoriaDTO[]> {
    return this.http.get<CategoriaDTO[]>(this.url);
  }

  guardar(categoria: any): Observable<any> {
    // Recuerda: responseType text para evitar errores de parseo
    return this.http.post(this.url, categoria, { responseType: 'text' });
  }

  // Si tu backend tiene delete, agrégalo. Si no, solo listar y crear.
  /* eliminar(id: number) { ... } */
}
