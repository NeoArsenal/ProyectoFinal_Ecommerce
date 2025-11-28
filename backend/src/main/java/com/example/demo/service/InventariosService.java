package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.InventarioDTO;

public interface InventariosService {
    List<InventarioDTO> listar();
    
    // Guardar o Actualizar Stock en un almacén
    int guardar(InventarioDTO dto);
    
    void eliminar(Integer id);
}
