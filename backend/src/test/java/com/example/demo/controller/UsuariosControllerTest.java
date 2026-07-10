package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.PerfilDTO;
import com.example.demo.dto.UsuariosDTO;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.UsuariosService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class UsuariosControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuariosService service;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UsuariosController controller;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testLoginExitoso() throws Exception {
        LoginDTO credenciales = new LoginDTO();
        credenciales.setEmail("test@test.com");
        credenciales.setPassword("password");

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtProvider.generarToken(auth)).thenReturn("fake-jwt-token");
        
        UsuariosDTO usuarioDto = new UsuariosDTO();
        usuarioDto.setId(1);
        usuarioDto.setEmail("test@test.com");
        when(service.login("test@test.com", "password")).thenReturn(usuarioDto);

        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(credenciales)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.usuario.email").value("test@test.com"));
    }

    @Test
    void testLoginFallido() throws Exception {
        LoginDTO credenciales = new LoginDTO();
        credenciales.setEmail("bad@test.com");
        credenciales.setPassword("bad-password");

        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Credenciales incorrectas"));

        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(credenciales)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testListar() throws Exception {
        when(service.listar()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testVerPerfil() throws Exception {
        PerfilDTO perfil = new PerfilDTO();
        perfil.setNombre("Juan");
        
        when(service.obtenerPerfil(1)).thenReturn(perfil);

        mockMvc.perform(get("/usuarios/1/perfil"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }
    @Test
    void testRegistrarExitoso() throws Exception {
        UsuariosDTO dto = new UsuariosDTO();
        dto.setEmail("nuevo@test.com");
        dto.setPassword("123456");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setGenero("M");
        dto.setDni("12345678");
        
        when(service.registrar(any())).thenReturn(dto);

        mockMvc.perform(post("/usuarios/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testRegistrarFallido() throws Exception {
        UsuariosDTO dto = new UsuariosDTO();
        dto.setEmail("nuevo@test.com");
        dto.setPassword("123456");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setGenero("M");
        dto.setDni("12345678");
        
        when(service.registrar(any())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(post("/usuarios/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGuardarPerfilExitoso() throws Exception {
        PerfilDTO dto = new PerfilDTO();
        dto.setNombre("Carlos");
        
        when(service.guardarPerfil(any(Integer.class), any())).thenReturn(1);

        mockMvc.perform(put("/usuarios/1/perfil")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGuardarPerfilFallido() throws Exception {
        PerfilDTO dto = new PerfilDTO();
        
        when(service.guardarPerfil(any(Integer.class), any())).thenReturn(0);

        mockMvc.perform(put("/usuarios/1/perfil")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAsignarRoles() throws Exception {
        mockMvc.perform(post("/usuarios/1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[1, 2]"))
                .andExpect(status().isOk());
    }
}
