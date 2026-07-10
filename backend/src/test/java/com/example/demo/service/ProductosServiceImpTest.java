package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.ProductosDTO;
import com.example.demo.model.Productos;
import com.example.demo.repository.ProductosRepository;

@ExtendWith(MockitoExtension.class)
@org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class ProductosServiceImpTest {

    @Mock
    private ProductosRepository productosRepo;

    @InjectMocks
    private ProductosServiceImp service;

    @Test
    void testGuardarExcepcion() {
        when(productosRepo.save(any(Productos.class))).thenThrow(new RuntimeException("DB error"));
        
        ProductosDTO dto = new ProductosDTO();
        int res = service.save(dto);
        assertEquals(0, res);
    }
}
