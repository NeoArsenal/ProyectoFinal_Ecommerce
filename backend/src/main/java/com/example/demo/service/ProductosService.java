package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.ProductosDTO;


public interface ProductosService {

    // 1. Listar: Devuelve DTOs (con nombres de categoría y proveedores)
    List<ProductosDTO> listar();

    // 2. Guardar: Recibe DTO (con IDs de proveedores)
    // Retorna 1 si éxito, 0 si error
    int save(ProductosDTO dto);

    // 3. Eliminar
    void delete(Integer id);

    // 4. Buscar por ID (Opcional, pero útil si el Controller lo pide)
    Optional<ProductosDTO> buscar(Integer id);
}
