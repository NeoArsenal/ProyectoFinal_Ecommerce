package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductosDTO;
import com.example.demo.model.Productos;
import com.example.demo.model.Proveedores;
import com.example.demo.repository.CategoriasRepository;
import com.example.demo.repository.ProductosRepository;
import com.example.demo.repository.ProveedoresRepository;

@Service
public class ProductosServiceImp implements ProductosService {

    @Autowired private ProductosRepository productoRepo;
    @Autowired private CategoriasRepository categoriaRepo;
    @Autowired private ProveedoresRepository proveedorRepo;

    @Override
    public List<ProductosDTO> listar() {
        List<Productos> lista = productoRepo.findAll();
        List<ProductosDTO> dtos = new ArrayList<>();
        for (Productos p : lista) {
            dtos.add(convertirADTO(p));
        }
        return dtos;
    }

    // --- GUARDAR CON RELACIÓN N:M ---
    @Override
    public int save(ProductosDTO dto) {
        try {
            Productos p = new Productos();
            
            // Si viene ID > 0, es actualización: buscamos el existente para no perder datos
            if (dto.getId() != null && dto.getId() > 0) {
                p = productoRepo.findById(dto.getId()).orElse(new Productos());
            }

            p.setNombre(dto.getNombre());
            p.setDescripcion(dto.getDescripcion());
            p.setPrecio(dto.getPrecio());
            p.setStock(dto.getStock());

            // 1. Asignar Categoría
            if (dto.getCategoriaId() != null) {
                categoriaRepo.findById(dto.getCategoriaId()).ifPresent(p::setCategoria);
            }

            // 2. Asignar Proveedores (La magia N:M)
            if (dto.getProveedorIds() != null && !dto.getProveedorIds().isEmpty()) {
                List<Proveedores> listaProv = proveedorRepo.findAllById(dto.getProveedorIds());
                p.setProveedores(listaProv);
            } else {
                p.setProveedores(new ArrayList<>());
            }

            return productoRepo.save(p) != null ? 1 : 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void delete(Integer id) {
        productoRepo.deleteById(id);
    }

    // --- ESTE ES EL MÉTODO QUE FALTABA ---
    @Override
    public Optional<ProductosDTO> buscar(Integer id) {
        // Buscamos por ID y si existe, lo convertimos a DTO
        return productoRepo.findById(id).map(this::convertirADTO);
    }

    // --- Helper para convertir Entidad a DTO ---
    private ProductosDTO convertirADTO(Productos p) {
        ProductosDTO dto = new ProductosDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());

        if (p.getCategoria() != null) {
            dto.setCategoriaId(p.getCategoria().getId());
            dto.setCategoriaNombre(p.getCategoria().getNombre());
        }

        // Llenar datos de proveedores para verlos en la tabla
        if (p.getProveedores() != null) {
            dto.setProveedorIds(p.getProveedores().stream()
                    .map(Proveedores::getId)
                    .collect(Collectors.toList()));
            
            dto.setProveedorNombres(p.getProveedores().stream()
                    .map(Proveedores::getEmpresa)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}