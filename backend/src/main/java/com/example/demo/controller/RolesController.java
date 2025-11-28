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

import com.example.demo.dto.RolesDTO;
import com.example.demo.service.RolesService;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "http://localhost:4200")
public class RolesController {

    @Autowired
    private RolesService service;

    @GetMapping
    public List<RolesDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody RolesDTO dto) {
        service.guardar(dto);
        return ResponseEntity.ok("Rol guardado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable  Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
