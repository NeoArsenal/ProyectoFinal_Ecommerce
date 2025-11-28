package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RolesDTO;

public interface RolesService {
    List<RolesDTO> listar();
    int guardar(RolesDTO rol);
    void eliminar(Integer id);
}
