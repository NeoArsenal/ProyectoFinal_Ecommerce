package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.time.LocalDateTime;
import com.example.demo.model.Pedidos;
import com.example.demo.model.Envios;
import com.example.demo.model.Usuarios;
import com.example.demo.model.Productos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.repository.EnviosRepository;
import com.example.demo.repository.PedidosRepository;
import com.example.demo.repository.ProductosRepository;
import com.example.demo.repository.UsuariosRepository;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidosRepository pedidosRepo;

    @Mock
    private ProductosRepository productosRepo;

    @Mock
    private EnviosRepository enviosRepo;

    @Mock
    private UsuariosRepository usuariosRepo;

    @InjectMocks
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dashboardController).build();
    }

    @Test
    void testObtenerResumen() throws Exception {
        when(pedidosRepo.sumTotalVentas()).thenReturn(1500.0);
        when(pedidosRepo.count()).thenReturn(20L);
        when(productosRepo.countProductosBajoStock()).thenReturn(5L);

        mockMvc.perform(get("/dashboard/resumen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalVentas").value(1500.0))
                .andExpect(jsonPath("$.cantidadPedidos").value(20))
                .andExpect(jsonPath("$.productosBajoStock").value(5));
    }

    @Test
    void testObtenerTendencia() throws Exception {
        Pedidos p = new Pedidos();
        p.setFecha(LocalDateTime.now());
        p.setTotal(100.0);
        when(pedidosRepo.findAll()).thenReturn(Arrays.asList(p));

        mockMvc.perform(get("/dashboard/tendencia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(7));
    }

    @Test
    void testObtenerActividades() throws Exception {
        Pedidos p = new Pedidos();
        p.setId(1);
        p.setFecha(LocalDateTime.now());
        p.setTotal(100.0);
        Usuarios u = new Usuarios();
        u.setEmail("test@test.com");
        u.setId(1);
        p.setUsuario(u);

        Envios e = new Envios();
        e.setId(1);
        e.setEstado("Enviado");
        e.setNumeroTracking("TRACK123");
        e.setPedido(p);

        Productos prod = new Productos();
        prod.setId(1);
        prod.setNombre("Producto 1");

        when(pedidosRepo.findAll()).thenReturn(Arrays.asList(p));
        when(enviosRepo.findAll()).thenReturn(Arrays.asList(e));
        when(usuariosRepo.findAll()).thenReturn(Arrays.asList(u));
        when(productosRepo.findAll()).thenReturn(Arrays.asList(prod));

        mockMvc.perform(get("/dashboard/actividades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }
}
