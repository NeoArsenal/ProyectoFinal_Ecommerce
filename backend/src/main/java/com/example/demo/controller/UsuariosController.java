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

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuariosController {

    @Autowired
    private UsuariosService service;
    
    @GetMapping
    public ResponseEntity<List<UsuariosDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuariosDTO credenciales) {
        UsuariosDTO usuario = service.login(credenciales.getEmail(), credenciales.getPassword());
        
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody UsuariosDTO nuevoUsuario) {
        try {
            UsuariosDTO creado = service.registrar(nuevoUsuario);
            return ResponseEntity.ok(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}/perfil")
    public ResponseEntity<PerfilDTO> verPerfil(@PathVariable Integer id) {
        PerfilDTO perfil = service.obtenerPerfil(id);
        if (perfil != null) {
            return ResponseEntity.ok(perfil);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/perfil")
    public ResponseEntity<?> guardarPerfil(@PathVariable Integer id, @RequestBody PerfilDTO dto) {
        int ok = service.guardarPerfil(id, dto);
        if (ok == 1) {
            return ResponseEntity.ok("Perfil actualizado correctamente");
        }
        return ResponseEntity.badRequest().body("Error al actualizar perfil");
    }
    
    @PostMapping("/{id}/roles")
    public ResponseEntity<?> asignarRoles(@PathVariable Integer id, @RequestBody List<Integer> rolesIds) {
        service.asignarRoles(id, rolesIds);
        // Devolvemos texto plano para que Angular no sufra parseando JSON vacío
        return ResponseEntity.ok("Roles actualizados");
    }
}
