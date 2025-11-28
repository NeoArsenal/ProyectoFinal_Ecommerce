package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.InventarioDTO;
import com.example.demo.service.InventariosService;

@RestController
@RequestMapping("/inventarios")
@CrossOrigin(origins = "http://localhost:4200") // Opcional si ya tienes el Config global
public class InventariosController {

    @Autowired
    private InventariosService service;

    @GetMapping
    public List<InventarioDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody InventarioDTO dto) {
        int res = service.guardar(dto);
        if (res == 1) {
            return ResponseEntity.ok("Inventario actualizado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al guardar inventario");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
