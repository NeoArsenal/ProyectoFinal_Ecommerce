import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlmacenesService, AlmacenDTO } from '../services/almacenes';

@Component({
  selector: 'app-almacenes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './almacenes.html',
  styleUrls: ['./almacenes.css'] // Reusaremos estilos de proveedores
})
export class Almacenes implements OnInit {
  
  almacenes: AlmacenDTO[] = [];
  form: FormGroup;

  constructor(private fb: FormBuilder, private service: AlmacenesService) {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      ubicacion: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.service.listar().subscribe(data => this.almacenes = data);
  }

  guardar() {
    if (this.form.invalid) return;
    
    this.service.crear(this.form.value).subscribe(() => {
      this.cargar();
      this.form.reset();
    });
  }

  borrar(id: number) {
    if(confirm('¿Eliminar almacén?')) {
      this.service.eliminar(id).subscribe(() => this.cargar());
    }
  }
}
