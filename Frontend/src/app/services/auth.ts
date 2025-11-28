import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private url = 'http://localhost:8085/usuarios';
  
  // Variable para saber quién está logueado
  private usuarioActual: any = null;

  constructor(private http: HttpClient) {
    // Al iniciar, intentamos recuperar el usuario del "disco duro" del navegador
    const guardado = localStorage.getItem('usuario_ecommerce');
    if (guardado) {
      this.usuarioActual = JSON.parse(guardado);
    }
  }

  // --- LOGIN ---
  login(credenciales: any): Observable<any> {
    return this.http.post(`${this.url}/login`, credenciales).pipe(
      tap((usuario: any) => {
        this.guardarSesion(usuario);
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
    localStorage.removeItem('usuario_ecommerce');
    window.location.href = '/login'; // Recargar página
  }

  // --- UTILIDADES ---
  
  // Guardar en memoria y localStorage
  private guardarSesion(usuario: any) {
    this.usuarioActual = usuario;
    localStorage.setItem('usuario_ecommerce', JSON.stringify(usuario));
  }

  get usuario() {
    return this.usuarioActual;
  }

  get estaLogueado(): boolean {
    return this.usuarioActual != null;
  }
  obtenerPerfil(id: number): Observable<any> {
    return this.http.get(`${this.url}/${id}/perfil`);
  }

  guardarPerfil(id: number, datos: any): Observable<any> {
    return this.http.post(`${this.url}/${id}/perfil`, datos, { responseType: 'text' });
  }
}
