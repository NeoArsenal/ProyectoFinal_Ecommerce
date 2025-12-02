package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PerfilDTO;
import com.example.demo.dto.UsuariosDTO;
import com.example.demo.service.UsuariosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuariosController {

    @Autowired
    private UsuariosService service;

    // --- 1. Login Validado ---
    @PostMapping("/login")
    // Agregamos @Valid antes de @RequestBody
    public ResponseEntity<?> login(@Valid @RequestBody UsuariosDTO credenciales) {
        UsuariosDTO usuario = service.login(credenciales.getEmail(), credenciales.getPassword());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
    }

    // --- 2. Registro Validado ---
    @PostMapping("/registro")
    // Agregamos @Valid aquí también
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuariosDTO nuevoUsuario) {
        try {
            UsuariosDTO creado = service.registrar(nuevoUsuario);
            return ResponseEntity.ok(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- 3. Listar ---
    @GetMapping
    public ResponseEntity<List<UsuariosDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // --- 4. Perfil (Ver) ---
    @GetMapping("/{id}/perfil")
    public ResponseEntity<PerfilDTO> verPerfil(@PathVariable Integer id) {
        PerfilDTO perfil = service.obtenerPerfil(id);
        if (perfil != null) {
            return ResponseEntity.ok(perfil);
        }
        return ResponseEntity.notFound().build();
    }

    // --- 5. Perfil (Guardar) ---
    @PostMapping("/{id}/perfil")
    public ResponseEntity<?> guardarPerfil(@PathVariable Integer id, @RequestBody PerfilDTO dto) {
        int ok = service.guardarPerfil(id, dto);
        if (ok == 1) {
            return ResponseEntity.ok("Perfil actualizado correctamente");
        }
        return ResponseEntity.badRequest().body("Error al actualizar perfil");
    }

    // --- 6. Asignar Roles ---
    @PostMapping("/{id}/roles")
    public ResponseEntity<?> asignarRoles(@PathVariable Integer id, @RequestBody List<Integer> rolesIds) {
        service.asignarRoles(id, rolesIds);
        return ResponseEntity.ok("Roles actualizados");
    }
}