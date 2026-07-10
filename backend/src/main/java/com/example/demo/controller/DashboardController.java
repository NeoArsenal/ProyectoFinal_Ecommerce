package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.dto.ActividadDTO;
import com.example.demo.model.Pedidos;
import com.example.demo.model.Envios;
import com.example.demo.model.Usuarios;
import com.example.demo.model.Productos;
import com.example.demo.repository.PedidosRepository;
import com.example.demo.repository.ProductosRepository;
import com.example.demo.repository.EnviosRepository;
import com.example.demo.repository.UsuariosRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    private final PedidosRepository pedidosRepo;
    private final ProductosRepository productosRepo;
    private final EnviosRepository enviosRepo;
    private final UsuariosRepository usuariosRepo;

    @Autowired
    public DashboardController(PedidosRepository pedidosRepo, ProductosRepository productosRepo,
                               EnviosRepository enviosRepo, UsuariosRepository usuariosRepo) {
        this.pedidosRepo = pedidosRepo;
        this.productosRepo = productosRepo;
        this.enviosRepo = enviosRepo;
        this.usuariosRepo = usuariosRepo;
    }

    @GetMapping("/resumen")
    public ResponseEntity<DashboardDTO> obtenerResumen() {
        Double total = pedidosRepo.sumTotalVentas();
        Long cantidad = pedidosRepo.count();
        Long bajoStock = productosRepo.countProductosBajoStock();

        DashboardDTO dto = new DashboardDTO(total, cantidad, bajoStock);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/tendencia")
    public ResponseEntity<List<Double>> obtenerTendencia() {
        List<Pedidos> pedidos = pedidosRepo.findAll();
        
        // Agrupar ventas por día
        Map<LocalDate, Double> ventasPorDia = new HashMap<>();
        for (Pedidos p : pedidos) {
            if (p.getFecha() != null) {
                LocalDate dia = p.getFecha().toLocalDate();
                ventasPorDia.put(dia, ventasPorDia.getOrDefault(dia, 0.0) + (p.getTotal() != null ? p.getTotal() : 0.0));
            }
        }
        
        // Generar lista de los últimos 7 días terminando en "hoy"
        List<Double> tendencia = new ArrayList<>();
        LocalDate hoy = LocalDate.now(java.time.ZoneId.systemDefault());
        for (int i = 6; i >= 0; i--) {
            LocalDate dia = hoy.minusDays(i);
            tendencia.add(ventasPorDia.getOrDefault(dia, 0.0));
        }
        
        return ResponseEntity.ok(tendencia);
    }

    @GetMapping("/actividades")
    public ResponseEntity<List<ActividadDTO>> obtenerActividades() {
        List<ActividadDTO> lista = new ArrayList<>();
        
        // 1. Cargar Pedidos Recientes (Máximo 3)
        List<Pedidos> pedidos = pedidosRepo.findAll();
        // Ordenar por fecha descendente
        pedidos.sort((p1, p2) -> p2.getFecha().compareTo(p1.getFecha()));
        for (int i = 0; i < Math.min(pedidos.size(), 3); i++) {
            Pedidos p = pedidos.get(i);
            String emailCliente = p.getUsuario() != null ? p.getUsuario().getEmail() : "Cliente";
            lista.add(new ActividadDTO(
                "Pedido #" + p.getId() + " registrado por S/ " + String.format("%.2f", p.getTotal()) + " (" + emailCliente + ")",
                p.getFecha(),
                "shopping_cart",
                "text-blue-600 bg-blue-50"
            ));
        }

        // 2. Cargar Envíos Recientes (Máximo 3)
        List<Envios> envios = enviosRepo.findAll();
        for (int i = 0; i < Math.min(envios.size(), 3); i++) {
            Envios e = envios.get(i);
            LocalDateTime fechaEnvio = e.getPedido() != null ? e.getPedido().getFecha() : LocalDateTime.now(java.time.ZoneId.systemDefault());
            lista.add(new ActividadDTO(
                "Envío #" + e.getId() + " en estado '" + e.getEstado() + "' (Tracking: " + e.getNumeroTracking() + ")",
                fechaEnvio,
                "local_shipping",
                "text-sky-600 bg-sky-50"
            ));
        }

        // 3. Cargar Usuarios Registrados (Máximo 2)
        List<Usuarios> usuarios = usuariosRepo.findAll();
        for (int i = 0; i < Math.min(usuarios.size(), 2); i++) {
            Usuarios u = usuarios.get(i);
            // Simular fecha de registro según ID decreciente para poner los más nuevos al inicio
            LocalDateTime fechaReg = LocalDateTime.now(java.time.ZoneId.systemDefault()).minusHours(24L - (long) u.getId() * 2L);
            lista.add(new ActividadDTO(
                "Usuario " + u.getEmail() + " registrado en el sistema",
                fechaReg,
                "group",
                "text-violet-600 bg-violet-50"
            ));
        }

        // 4. Cargar Productos Registrados (Máximo 2)
        List<Productos> productos = productosRepo.findAll();
        for (int i = 0; i < Math.min(productos.size(), 2); i++) {
            Productos prod = productos.get(i);
            // Simular fecha de creación en base a ID
            LocalDateTime fechaProd = LocalDateTime.now(java.time.ZoneId.systemDefault()).minusHours(72L - (long) prod.getId() * 3L);
            lista.add(new ActividadDTO(
                "Producto '" + prod.getNombre() + "' agregado al catálogo de ventas",
                fechaProd,
                "inventory_2",
                "text-emerald-600 bg-emerald-50"
            ));
        }

        // Ordenar todas las actividades de manera global
        Collections.sort(lista);
        
        // Retornar las últimas 8 actividades más recientes
        List<ActividadDTO> resultado = lista.subList(0, Math.min(lista.size(), 8));
        return ResponseEntity.ok(resultado);
    }
}
