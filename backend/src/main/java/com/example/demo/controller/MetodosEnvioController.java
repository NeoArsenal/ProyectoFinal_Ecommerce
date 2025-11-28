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

import com.example.demo.dto.MetodoEnvioDTO;
import com.example.demo.service.MetodosEnvioService;

@RestController
@RequestMapping("/metodos-envio")
@CrossOrigin(origins = "http://localhost:4200")
public class MetodosEnvioController {

    @Autowired
    private MetodosEnvioService service;

    @GetMapping
    public List<MetodoEnvioDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody MetodoEnvioDTO dto) {
        service.guardar(dto);
        return ResponseEntity.ok("Método guardado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
