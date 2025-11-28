package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PerfilDTO;
import com.example.demo.dto.UsuariosDTO;

public interface UsuariosService {
    // Retorna el usuario si el login es correcto, o null/error si falla
    UsuariosDTO login(String email, String password);
    
    // Registra un nuevo usuario
    UsuariosDTO registrar(UsuariosDTO usuarioDto);
    List<UsuariosDTO> listar();
    
 // Obtener perfil de un usuario
    PerfilDTO obtenerPerfil(Integer usuarioId);
    
    // Guardar/Actualizar perfil
    int guardarPerfil(Integer usuarioId, PerfilDTO perfil);
    
    //Metodo para roles
    void asignarRoles(Integer usuarioId, List<Integer> rolesIds);
    List<Integer> obtenerRolesIds(Integer usuarioId);
}
