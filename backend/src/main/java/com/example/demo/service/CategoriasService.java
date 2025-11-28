package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.CategoriasDTO;
import com.example.demo.model.Categorias;

public interface CategoriasService {

    // Métodos para la Vista (DTOs)
    List<CategoriasDTO> listar();
    Optional<CategoriasDTO> buscar(Integer id);

    // Métodos CRUD (Entidades)
    // El 'int' indica 1 si se guardó, 0 si falló
    int save(Categorias categoria);
    void delete(Integer id);
    
    // Auxiliar para buscar entidad completa (útil para validaciones internas)
    Optional<Categorias> listarId(Integer id);
}
