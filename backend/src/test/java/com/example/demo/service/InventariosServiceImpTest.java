package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.InventarioDTO;
import com.example.demo.model.Almacenes;
import com.example.demo.model.Inventarios;
import com.example.demo.model.Productos;
import com.example.demo.repository.AlmacenesRepository;
import com.example.demo.repository.InventariosRepository;
import com.example.demo.repository.ProductosRepository;

@ExtendWith(MockitoExtension.class)
class InventariosServiceImpTest {

    @Mock
    private InventariosRepository invRepo;

    @Mock
    private ProductosRepository prodRepo;

    @Mock
    private AlmacenesRepository almRepo;

    @InjectMocks
    private InventariosServiceImp service;

    @Test
    void testListar() {
        Inventarios inv = new Inventarios();
        inv.setId(1);
        inv.setCantidad(50);
        
        Productos p = new Productos();
        p.setId(1);
        p.setNombre("Prod1");
        inv.setProducto(p);
        
        Almacenes a = new Almacenes();
        a.setId(1);
        a.setNombre("Almacen1");
        inv.setAlmacen(a);
        
        when(invRepo.findAll()).thenReturn(Collections.singletonList(inv));
        
        var lista = service.listar();
        assertEquals(1, lista.size());
        assertEquals("Prod1", lista.get(0).getProductoNombre());
    }

    @Test
    void testGuardarExitoso() {
        InventarioDTO dto = new InventarioDTO();
        dto.setProductoId(1);
        dto.setAlmacenId(1);
        dto.setCantidad(100);

        Productos p = new Productos();
        p.setId(1);
        p.setStock(0);
        
        Almacenes a = new Almacenes();
        a.setId(1);

        when(prodRepo.findById(1)).thenReturn(Optional.of(p));
        when(almRepo.findById(1)).thenReturn(Optional.of(a));
        when(invRepo.findByProductoIdAndAlmacenId(1, 1)).thenReturn(Optional.empty());
        when(invRepo.save(any())).thenReturn(new Inventarios());
        when(prodRepo.save(any())).thenReturn(p);

        int res = service.guardar(dto);
        assertEquals(1, res);
        assertEquals(100, p.getStock());
    }

    @Test
    void testGuardarFallido() {
        InventarioDTO dto = new InventarioDTO();
        dto.setProductoId(1);
        dto.setAlmacenId(1);
        
        when(prodRepo.findById(1)).thenReturn(Optional.empty());
        
        int res = service.guardar(dto);
        assertEquals(0, res);
    }
    @Test
    void testGuardarFallidoAlmacen() {
        InventarioDTO dto = new InventarioDTO();
        dto.setProductoId(1);
        dto.setAlmacenId(1);
        
        when(prodRepo.findById(1)).thenReturn(Optional.of(new Productos()));
        when(almRepo.findById(1)).thenReturn(Optional.empty());
        
        int res = service.guardar(dto);
        assertEquals(0, res);
    }
    
    @Test
    void testGuardarExcepcion() {
        InventarioDTO dto = new InventarioDTO();
        dto.setProductoId(1);
        dto.setAlmacenId(1);
        
        when(prodRepo.findById(1)).thenThrow(new RuntimeException("DB error"));
        
        int res = service.guardar(dto);
        assertEquals(0, res);
    }
}
