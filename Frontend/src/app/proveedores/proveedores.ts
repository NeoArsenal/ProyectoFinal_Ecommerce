import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProveedoresService, ProveedorDTO, ProveedorPayload } from '../services/proveedores';

@Component({
  selector: 'app-proveedores',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proveedores.html',
  styleUrls: ['./proveedores.css']
})
export class Proveedores implements OnInit {
  
  proveedores: ProveedorDTO[] = [];
  form: FormGroup;

  constructor(private fb: FormBuilder, private service: ProveedoresService) {
    this.form = this.fb.group({
      id: [0],
      empresa: ['', [Validators.required, Validators.maxLength(100)]],
      contacto: ['', [Validators.required]],
      telefono: ['', [Validators.required, Validators.pattern('^[0-9]*$')]] // Solo números
    });
  }

  ngOnInit(): void {
    this.listar();
  }

  // Getters para el HTML
  get empresaCtl() { return this.form.get('empresa'); }
  get contactoCtl() { return this.form.get('contacto'); }
  get telCtl() { return this.form.get('telefono'); }

  get esEdicion(): boolean { return this.form.get('id')?.value > 0; }

  listar() {
    this.service.listar().subscribe(data => this.proveedores = data);
  }

  guardar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload: ProveedorPayload = {
      empresa: this.empresaCtl?.value,
      contacto: this.contactoCtl?.value,
      telefono: this.telCtl?.value
    };

    const id = this.form.get('id')?.value;

    if (id > 0) {
      // Modificar
      this.service.modificar(id, payload).subscribe(() => {
        this.listar();
        this.limpiar();
      });
    } else {
      // Crear
      this.service.insertar(payload).subscribe(() => {
        this.listar();
        this.limpiar();
      });
    }
  }

  editar(p: ProveedorDTO) {
    this.form.patchValue(p);
  }

  borrar(id: number) {
    if(confirm("¿Seguro que deseas eliminar este proveedor?")) {
      this.service.eliminar(id).subscribe(() => this.listar());
    }
  }

  limpiar() {
    this.form.reset({ id: 0 });
  }
}
