package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.AlmacenesDTO;
import com.example.demo.service.AlmacenesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class AlmacenesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlmacenesService service;

    @InjectMocks
    private AlmacenesController controller;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListar() throws Exception {
        when(service.listar()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/almacenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testCrearExitoso() throws Exception {
        AlmacenesDTO dto = new AlmacenesDTO();
        dto.setId(1);
        dto.setNombre("Almacen Norte");
        dto.setUbicacion("Lima");
        
        when(service.save(any())).thenReturn(1);

        mockMvc.perform(post("/almacenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Almacén guardado"));
    }

    @Test
    void testCrearFallido() throws Exception {
        AlmacenesDTO dto = new AlmacenesDTO();
        dto.setId(1);
        dto.setNombre("Almacen Norte");
        
        when(service.save(any())).thenReturn(0);

        mockMvc.perform(post("/almacenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEliminar() throws Exception {
        mockMvc.perform(delete("/almacenes/1"))
                .andExpect(status().isNoContent());
    }
}
