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

// Guard sólo para administradores
export const adminGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.estaLogueado && authService.isAdmin()) {
    return true;
  }
  // Si no es admin, redirige a Home (o a una página de acceso denegado si existe)
  router.navigate(['/home']);
  return false;
};

// Guard para páginas de invitado (login/registro): si ya está logueado, redirige a Home
export const guestGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.estaLogueado) {
    router.navigate(['/home']);
    return false;
  }
  return true;
};
