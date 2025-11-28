import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './perfil.html',
  styleUrls: ['./perfil.css'] // Reusa estilos si quieres
})
export class Perfil implements OnInit {
  
  perfil: any = { direccion: '', telefono: '', dni: '', email: '' };
  usuarioId: number = 0;

  constructor(private auth: AuthService) {
    if (this.auth.usuario) {
      this.usuarioId = this.auth.usuario.id;
    }
  }

  ngOnInit(): void {
    if (this.usuarioId > 0) {
      this.auth.obtenerPerfil(this.usuarioId).subscribe(data => this.perfil = data);
    }
  }

  guardar() {
    this.auth.guardarPerfil(this.usuarioId, this.perfil).subscribe({
      next: (resp) => alert(resp),
      error: (err) => alert('Error al guardar')
    });
  }
}
