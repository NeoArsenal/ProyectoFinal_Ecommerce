package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EnvioDTO;
import com.example.demo.repository.EnviosService;

@RestController
@RequestMapping("/envios")
@CrossOrigin(origins = "http://localhost:4200")
public class EnviosController {

    @Autowired
    private EnviosService service;

    @GetMapping
    public List<EnvioDTO> listar() {
        return service.listar();
    }

    // PATCH es ideal para actualizaciones parciales (solo el estado)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id, @RequestParam String estado) {
        service.actualizarEstado(id, estado);
        return ResponseEntity.ok().build();
    }
}
