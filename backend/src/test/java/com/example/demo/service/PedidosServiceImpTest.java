package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.dto.VentaDTO;
import com.example.demo.repository.EnviosRepository;
import com.example.demo.repository.MetodosEnvioRepository;
import com.example.demo.repository.PagosRepository;
import com.example.demo.repository.PedidosRepository;
import com.example.demo.repository.ProductosRepository;
import com.example.demo.repository.UsuariosRepository;

@ExtendWith(MockitoExtension.class)
class PedidosServiceImpTest {

    @Mock
    private PedidosRepository pedidosRepo;

    @Mock
    private ProductosRepository productosRepo;

    @Mock
    private UsuariosRepository usuariosRepo;

    @Mock
    private PagosRepository pagosRepo;

    @Mock
    private EnviosRepository enviosRepo;

    @Mock
    private MetodosEnvioRepository metodosEnvioRepo;

    @InjectMocks
    private PedidosServiceImp pedidosService;

    @Test
    void testRegistrarVentaFallaNoAutenticado() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@test.com");
        SecurityContextHolder.setContext(securityContext);
        
        when(usuariosRepo.findByEmail("test@test.com")).thenReturn(Optional.empty());

        int result = pedidosService.registrarVenta(new VentaDTO());
        assertEquals(0, result);
        
        SecurityContextHolder.clearContext();
    }
}
