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
    // Evitar recarga completa; permitir que guards/plantillas reaccionen correctamente
    try {
      // Router navigation es preferible para SPA
      // Nota: usamos navegación por URL para no acoplar el servicio con Router directamente
      history.pushState(null, '', '/login');
      window.dispatchEvent(new Event('popstate'));
    } catch {
      // Fallback: recargar si algo falla
      window.location.href = '/login';
    }
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

  // --- ROLES Y PERMISOS ---
  // Obtiene el usuario actual (claims/roles deberían venir del backend al hacer login)
  getCurrentUser() {
    return this.usuarioActual;
  }

  // Devuelve arreglo de roles si existe, o [] si no
  private getRoles(): string[] {
    const user = this.getCurrentUser();
    const roles = Array.isArray(user?.roles) ? user.roles : [];
    // También soporta roles como string separado por comas
    if (!roles.length && typeof user?.roles === 'string') {
      return user.roles.split(',').map((r: string) => r.trim());
    }
    return roles;
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  isAdmin(): boolean {
    return this.hasRole('ROLE_ADMIN');
  }

  isUser(): boolean {
    return this.hasRole('ROLE_USER');
  }
  obtenerPerfil(id: number): Observable<any> {
    return this.http.get(`${this.url}/${id}/perfil`);
  }

  guardarPerfil(id: number, datos: any): Observable<any> {
    return this.http.post(`${this.url}/${id}/perfil`, datos, { responseType: 'text' });
  }
}
