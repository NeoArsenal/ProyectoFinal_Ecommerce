import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// --- INTERFACES (Estructura de datos) ---
export interface VentaPayload {
  usuarioId: number;
  items: ItemVenta[];
}

export interface ItemVenta {
  productoId: number;
  cantidad: number;
  precio: number;
  // Opcionales solo para la vista
  nombreProducto?: string;
  subtotal?: number;
}

@Injectable({ providedIn: 'root' })
export class VentasService {
  // Asegúrate que el puerto coincida con tu Spring Boot (8085)
  private readonly url = 'http://localhost:8085/pedidos';

  constructor(private http: HttpClient) {}

  registrarVenta(venta: VentaPayload): Observable<any> {
    // Usamos responseType: 'text' porque el backend devuelve un String plano
    return this.http.post(this.url, venta, { responseType: 'text' });
  }
}