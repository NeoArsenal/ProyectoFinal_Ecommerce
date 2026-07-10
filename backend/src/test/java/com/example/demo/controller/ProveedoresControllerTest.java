package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.ProveedoresDTO;
import com.example.demo.model.Proveedores;
import com.example.demo.service.ProveedoresService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ProveedoresControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProveedoresService service;

    @InjectMocks
    private ProveedoresController controller;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListar() throws Exception {
        when(service.listar()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testObtener() throws Exception {
        ProveedoresDTO dto = new ProveedoresDTO();
        dto.setEmpresa("Empresa X");
        when(service.buscar(1)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empresa").value("Empresa X"));
    }

    @Test
    void testCrear() throws Exception {
        ProveedoresDTO dto = new ProveedoresDTO();
        dto.setId(1);
        dto.setEmpresa("Empresa Y");
        
        when(service.save(any())).thenReturn(1);

        mockMvc.perform(post("/proveedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testActualizar() throws Exception {
        ProveedoresDTO cambios = new ProveedoresDTO();
        cambios.setEmpresa("Empresa Z");

        Proveedores actual = new Proveedores();
        actual.setId(1);

        when(service.listarId(1)).thenReturn(Optional.of(actual));
        when(service.save(any())).thenReturn(1);

        mockMvc.perform(put("/proveedores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cambios)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminar() throws Exception {
        when(service.listarId(1)).thenReturn(Optional.of(new Proveedores()));

        mockMvc.perform(delete("/proveedores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCrearFallo() throws Exception {
        ProveedoresDTO dto = new ProveedoresDTO();
        dto.setId(1);
        dto.setEmpresa("Empresa Y");
        
        when(service.save(any())).thenReturn(0);

        mockMvc.perform(post("/proveedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testActualizarFallo() throws Exception {
        ProveedoresDTO cambios = new ProveedoresDTO();
        cambios.setEmpresa("Empresa Z");

        Proveedores actual = new Proveedores();
        actual.setId(1);

        when(service.listarId(1)).thenReturn(Optional.of(actual));
        when(service.save(any())).thenReturn(0);

        mockMvc.perform(put("/proveedores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cambios)))
                .andExpect(status().isBadRequest());
    }
}
