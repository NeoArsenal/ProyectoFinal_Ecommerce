import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Categoria {
  id: number;
  nombre: string;
}

export interface ProductoDTO {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  categoriaId: number;
  categoriaNombre: string;
  proveedorIds: number[];
  proveedorNombres?: string[];
}

export interface ProductoPayload {
  id?: number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  categoriaId: number;
  proveedorIds: number[];
}

@Injectable({ providedIn: 'root' })
export class ProductosService {
  private url = 'http://localhost:8085/productos';
  private urlCat = 'http://localhost:8085/categorias';

  constructor(private http: HttpClient) {}

  listar(): Observable<ProductoDTO[]> {
    return this.http.get<ProductoDTO[]>(this.url);
  }

  listarCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.urlCat);
  }

  insertar(prod: ProductoPayload): Observable<any> {
    // CORRECCIÓN: Agregamos { responseType: 'text' }
    return this.http.post(this.url, prod, { responseType: 'text' });
  }

  modificar(prod: ProductoPayload): Observable<any> {
    // CORRECCIÓN: Agregamos { responseType: 'text' }
    // Esto soluciona el error al editar productos
    return this.http.put(`${this.url}/${prod.id}`, prod, { responseType: 'text' });
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}