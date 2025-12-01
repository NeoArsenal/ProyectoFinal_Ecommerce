import { Routes } from '@angular/router';
import { Ventas } from './ventas/ventas'; 
import { Productos } from './productos/productos'; 
import { Proveedores } from './proveedores/proveedores';
import { Historial } from './historial/historial';
import { Home } from './home/home';
import { Login } from './login/login';
import { Registro } from './registro/registro';
import { authGuard, adminGuard, guestGuard } from './auth-guard';
import { Almacenes } from './almacenes/almacenes';
import { Inventarios } from './inventarios/inventarios';
import { Perfil } from './perfil/perfil';
import { Categorias } from './categorias/categorias';
import { Envios } from './envios/envios';
import { Metodos } from './metodos/metodos';
import { Roles } from './roles/roles';
import { Usuarios } from './usuarios/usuarios';

export const routes: Routes = [
  // Rutas Públicas (Cualquiera entra)
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login, canActivate: [guestGuard] },
  { path: 'registro', component: Registro, canActivate: [guestGuard] },

  // Rutas Privadas (Protegidas por el Guardián)
  { 
    path: 'home', 
    component: Home, 
    canActivate: [authGuard] // <--- ¡Seguridad activada!
  },
  { 
    path: 'ventas', 
    component: Ventas, 
    canActivate: [authGuard] 
  },
  { 
    path: 'productos', 
    component: Productos, 
    canActivate: [authGuard] 
  },
  { 
    path: 'proveedores', 
    component: Proveedores, 
    canActivate: [authGuard] 
  },
  { 
    path: 'historial', 
    component: Historial, 
    canActivate: [authGuard] 
  },
  { path: 'almacenes', component: Almacenes, canActivate: [authGuard] },
  { path: 'inventarios', component: Inventarios, canActivate: [authGuard] },
  { path: 'perfil', component: Perfil, canActivate: [authGuard] },
  { path: 'categorias', component: Categorias, canActivate: [authGuard] },
  { path: 'envios', component: Envios, canActivate: [authGuard] },
  { path: 'metodos', component: Metodos, canActivate: [authGuard] },
  { path: 'roles', component: Roles, canActivate: [authGuard, adminGuard] },
  { path: 'usuarios', component: Usuarios, canActivate: [authGuard] },
  // Usuarios: sólo administradores
  { path: 'usuarios', component: Usuarios, canActivate: [authGuard, adminGuard] },

  // Comodín
  { path: '**', redirectTo: 'login' }
];
