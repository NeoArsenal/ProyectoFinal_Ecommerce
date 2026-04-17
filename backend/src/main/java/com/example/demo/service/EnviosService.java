package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.EnvioDTO;

public interface EnviosService {
    List<EnvioDTO> listar();
    
    // Para cambiar de "PENDIENTE" a "ENVIADO"
    void actualizarEstado(Integer id, String nuevoEstado);
}