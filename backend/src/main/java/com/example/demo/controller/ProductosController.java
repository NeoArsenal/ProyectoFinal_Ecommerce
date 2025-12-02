package com.example.demo.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductosDTO;

import com.example.demo.service.ProductosService;

import jakarta.validation.Valid;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/productos")
public class ProductosController {

    @Autowired
    private ProductosService service;

    // 1. LISTAR (Devuelve DTOs con nombres de proveedores y categoría)
    @GetMapping
    public List<ProductosDTO> listar() {
        return service.listar();
    }

    // 2. BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> obtener(@PathVariable("id") Integer id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. CREAR (Recibe DTO para poder guardar múltiples proveedores)
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ProductosDTO nuevo) {
        int ok = service.save(nuevo);
        
        if (ok != 1) {
            return ResponseEntity.badRequest().body("Error al guardar el producto");
        }
        
        // Retornamos mensaje de éxito (o podrías retornar el DTO si ajustaras el servicio)
        return ResponseEntity.ok("Producto guardado con éxito");
    }

    // 4. ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Integer id,
                                        @Valid @RequestBody ProductosDTO cambios) {
        
        // Verificamos si existe antes de intentar actualizar
        if (service.buscar(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Aseguramos que el ID del DTO sea el de la URL
        cambios.setId(id);

        int ok = service.save(cambios);
        
        return (ok == 1) 
                ? ResponseEntity.ok("Producto actualizado") 
                : ResponseEntity.badRequest().body("Error al actualizar");
    }

    // 5. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        // Usamos buscar() para ver si existe, ya que devuelve Optional
        if (service.buscar(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}