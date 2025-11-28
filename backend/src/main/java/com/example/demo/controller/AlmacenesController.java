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

import com.example.demo.dto.AlmacenesDTO;
import com.example.demo.model.Almacenes;
import com.example.demo.service.AlmacenesService;

@RestController
@RequestMapping("/almacenes")
@CrossOrigin(origins = "http://localhost:4200")
public class AlmacenesController {

    @Autowired
    private AlmacenesService service;

    @GetMapping
    public List<AlmacenesDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Almacenes almacen) {
        int res = service.save(almacen);
        return res == 1 ? ResponseEntity.ok("Almacén guardado") : ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
