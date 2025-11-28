package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.MetodoEnvioDTO;

public interface MetodosEnvioService {
    List<MetodoEnvioDTO> listar();
    int guardar(MetodoEnvioDTO metodo);
    void eliminar(Integer id);
}
