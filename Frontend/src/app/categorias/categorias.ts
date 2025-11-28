import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoriasService, CategoriaDTO } from '../services/categorias';

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './categorias.html',
  styleUrls: ['./categorias.css']
})
export class Categorias implements OnInit {
  categorias: CategoriaDTO[] = [];
  form: FormGroup;

  constructor(private fb: FormBuilder, private service: CategoriasService) {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.cargar();
  }

  cargar() {
    this.service.listar().subscribe(data => this.categorias = data);
  }

  guardar() {
    if (this.form.invalid) return;
    
    this.service.guardar(this.form.value).subscribe({
      next: () => {
        this.cargar();
        this.form.reset();
      },
      error: (e) => alert('Error al guardar categoría')
    });
  }
}