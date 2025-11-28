package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.AlmacenesDTO;
import com.example.demo.model.Almacenes;

public interface AlmacenesService {
    List<AlmacenesDTO> listar();
    Optional<AlmacenesDTO> buscar(Integer id);
    int save(Almacenes almacen);
    void delete(Integer id);
}