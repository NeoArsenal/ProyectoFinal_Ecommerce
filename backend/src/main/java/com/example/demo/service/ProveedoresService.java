package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.ProveedoresDTO;
import com.example.demo.model.Proveedores;

public interface ProveedoresService {
    
    // Listar todo convertido a DTO
    List<ProveedoresDTO> listar();
    
    // Buscar por ID
    Optional<ProveedoresDTO> buscar(Integer id);

    // Guardar (Retorna 1 si éxito, 0 si error)
    int save(Proveedores proveedor);
    
    // Eliminar
    void delete(Integer id);
    
    // Auxiliar para buscar entidad completa (útil para validaciones internas)
    Optional<Proveedores> listarId(Integer id);
}