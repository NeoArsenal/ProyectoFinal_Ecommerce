package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.ProveedoresDTO;
import com.example.demo.model.Proveedores;
import com.example.demo.service.ProveedoresService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/proveedores")
public class ProveedoresController {

    @Autowired
    private ProveedoresService service;

    @GetMapping
    public List<ProveedoresDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedoresDTO> obtener(@PathVariable("id") Integer id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Proveedores> crear(@RequestBody Proveedores nuevo) {
        int ok = service.save(nuevo);
        
        if (ok != 1) {
            return ResponseEntity.badRequest().build();
        }
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevo.getId())
                .toUri();
        return ResponseEntity.created(location).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedores> actualizar(@PathVariable("id") Integer id,
                                                  @RequestBody Proveedores cambios) {
        
        Optional<Proveedores> opt = service.listarId(id);
        
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Proveedores actual = opt.get();
        actual.setEmpresa(cambios.getEmpresa());
        actual.setContacto(cambios.getContacto());
        actual.setTelefono(cambios.getTelefono());

        int ok = service.save(actual);
        
        return (ok == 1) ? ResponseEntity.ok(actual) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
        if (service.listarId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
