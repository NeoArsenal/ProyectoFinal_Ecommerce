package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.dto.*;
import com.example.demo.model.*;

import java.time.LocalDateTime;

class ModelAndDtoTest {

    @Test
    void testDashboardDTO() {
        DashboardDTO dto = new DashboardDTO(100.0, 5L, 2L);
        dto.setTotalVentas(200.0);
        dto.setCantidadPedidos(10L);
        dto.setProductosBajoStock(3L);
        
        assertEquals(200.0, dto.getTotalVentas());
        assertEquals(10L, dto.getCantidadPedidos());
        assertEquals(3L, dto.getProductosBajoStock());
    }

    @Test
    void testActividadDTO() {
        LocalDateTime now = LocalDateTime.now();
        ActividadDTO dto = new ActividadDTO("Test", now, "icon", "color");
        dto.setTexto("New Test");
        dto.setFechaRef(now.plusDays(1));
        dto.setIcono("new_icon");
        dto.setColor("new_color");
        
        assertEquals("New Test", dto.getTexto());
        assertEquals("new_icon", dto.getIcono());
        assertEquals("new_color", dto.getColor());
    }

    @Test
    void testUsuariosModel() {
        Usuarios u = new Usuarios();
        u.setId(1);
        u.setEmail("test@test.com");
        u.setPassword("pass");
        
        assertEquals(1, u.getId());
        assertEquals("test@test.com", u.getEmail());
        assertEquals("pass", u.getPassword());
    }
    
    @Test
    void testCategoriasModel() {
        Categorias c = new Categorias();
        c.setId(1);
        c.setNombre("Cat");
        c.setDescripcion("Desc");
        
        assertEquals(1, c.getId());
        assertEquals("Cat", c.getNombre());
        assertEquals("Desc", c.getDescripcion());
    }
}
