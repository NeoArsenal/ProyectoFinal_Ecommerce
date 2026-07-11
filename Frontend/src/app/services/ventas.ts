import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// --- INTERFACES ---
export interface VentaPayload {
  metodoPago:    string;   // "Efectivo" | "Tarjeta" | "Yape"
  metodoEnvioId: number;   // 1=Express, 2=Normal, etc.
  items: ItemVenta[];
}

export interface ItemVenta {
  productoId: number;
  cantidad:   number;
  precio:     number;
  // Solo para la vista local (no se envía al backend)
  nombreProducto?: string;
  subtotal?:       number;
}

export interface MetodoEnvio {
  id:     number;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class VentasService {
  private readonly url       = environment.apiUrl + '/pedidos';
  private readonly enviosUrl = environment.apiUrl + '/metodos-envio';

  constructor(private http: HttpClient) {}

  registrarVenta(venta: VentaPayload): Observable<any> {
    return this.http.post(this.url, venta, { responseType: 'text' });
  }

  listarMetodosEnvio(): Observable<MetodoEnvio[]> {
    return this.http.get<MetodoEnvio[]>(this.enviosUrl);
  }
}
