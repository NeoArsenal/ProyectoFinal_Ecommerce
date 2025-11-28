import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './services/auth';
import { inject } from '@angular/core';
export const authGuard: CanActivateFn = (route, state) => {
  // Inyectamos los servicios necesarios (en funciones no hay constructor)
  const authService = inject(AuthService);
  const router = inject(Router);

  // Verificamos si el usuario está logueado
  if (authService.estaLogueado) {
    return true; // ¡Pase usted!
  } else {
    // Si no está logueado, lo mandamos al Login
    router.navigate(['/login']);
    return false; // Acceso denegado
  }
};
