package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.CategoriasDTO;
import com.example.demo.service.CategoriasService;

@ExtendWith(MockitoExtension.class)
class CategoriasControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoriasService categoriasService;

    @InjectMocks
    private CategoriasController categoriasController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoriasController).build();
    }

    @Test
    void testListar() throws Exception {
        CategoriasDTO cat1 = new CategoriasDTO();
        cat1.setId(1);
        cat1.setNombre("Armas");
        
        CategoriasDTO cat2 = new CategoriasDTO();
        cat2.setId(2);
        cat2.setNombre("Munición");

        when(categoriasService.listar()).thenReturn(Arrays.asList(cat1, cat2));

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Armas"))
                .andExpect(jsonPath("$[1].nombre").value("Munición"));
    }

    @Test
    void testObtenerEncontrado() throws Exception {
        CategoriasDTO cat = new CategoriasDTO();
        cat.setId(1);
        cat.setNombre("Armas");
        
        when(categoriasService.buscar(1)).thenReturn(Optional.of(cat));

        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Armas"));
    }

    @Test
    void testObtenerNoEncontrado() throws Exception {
        when(categoriasService.buscar(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/categorias/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearExito() throws Exception {
        CategoriasDTO dto = new CategoriasDTO();
        dto.setId(1);
        dto.setNombre("Nueva");
        dto.setDescripcion("Desc");

        when(categoriasService.save(org.mockito.ArgumentMatchers.any(com.example.demo.model.Categorias.class))).thenReturn(1);

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/categorias")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCrearFallo() throws Exception {
        CategoriasDTO dto = new CategoriasDTO();
        dto.setId(1);
        dto.setNombre("Nueva");

        when(categoriasService.save(org.mockito.ArgumentMatchers.any(com.example.demo.model.Categorias.class))).thenReturn(0);

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/categorias")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
