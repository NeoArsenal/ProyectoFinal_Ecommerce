import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private url = environment.apiUrl + '/usuarios';
  
  // Variable para saber quién está logueado
  private usuarioActual: any = null;
  private tokenActual: string | null = null;

  constructor(private http: HttpClient) {
    // Al iniciar, intentamos recuperar solo el token del "disco duro" del navegador
    const tokenGuardado = localStorage.getItem('jwt_token');
    if (tokenGuardado) {
      try {
        // Validar expiración del token decodificando el payload
        const payload = JSON.parse(atob(tokenGuardado.split('.')[1]));
        if (payload.exp * 1000 < Date.now()) {
            this.logout();
        } else {
            this.tokenActual = tokenGuardado;
            // Reconstruimos un usuario básico a partir del token
            this.usuarioActual = { email: payload.sub };
        }
      } catch (e) {
        this.logout();
      }
    }
  }

  // --- LOGIN ---
  login(credenciales: any): Observable<any> {
    return this.http.post(`${this.url}/login`, credenciales).pipe(
      tap((response: any) => {
        // El backend ahora devuelve { token: "...", usuario: {...} }
        this.guardarSesion(response.token, response.usuario);
      })
    );
  }

  // --- REGISTRO ---
  registrar(datos: any): Observable<any> {
    return this.http.post(`${this.url}/registro`, datos);
  }

  // --- CERRAR SESIÓN ---
  logout() {
    this.usuarioActual = null;
    this.tokenActual = null;
    localStorage.removeItem('jwt_token');
    window.location.href = '/login'; // Recargar página
  }

  // --- UTILIDADES ---
  
  // Guardar en memoria y localStorage
  private guardarSesion(token: string, usuario: any) {
    this.tokenActual = token;
    this.usuarioActual = usuario;
    // MITIGACIÓN SEGURIDAD: Solo guardamos el token criptográfico, nunca los datos en texto plano
    localStorage.setItem('jwt_token', token);
  }

  get usuario() {
    return this.usuarioActual;
  }

  getToken() {
    return this.tokenActual;
  }

  get estaLogueado(): boolean {
    return this.tokenActual != null;
  }

  obtenerPerfil(id: number): Observable<any> {
    return this.http.get(`${this.url}/${id}/perfil`);
  }

  guardarPerfil(id: number, datos: any): Observable<any> {
    return this.http.put(`${this.url}/${id}/perfil`, datos, { responseType: 'text' });
  }
}

