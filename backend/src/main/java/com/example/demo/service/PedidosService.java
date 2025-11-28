package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.HistorialDTO;
import com.example.demo.dto.VentaDTO;
import com.example.demo.model.Pedidos;

public interface PedidosService {
    // Método transaccional complejo: Recibe DTO, guarda Entidades
    int registrarVenta(VentaDTO ventaDto);
    
    List<Pedidos> listar();
 
    List<HistorialDTO> listarHistorial();
}
