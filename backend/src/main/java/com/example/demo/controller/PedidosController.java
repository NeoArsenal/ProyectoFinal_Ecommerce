package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HistorialDTO;
import com.example.demo.dto.VentaDTO;
import com.example.demo.model.Pedidos;
import com.example.demo.service.PedidosService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pedidos")
public class PedidosController {

    @Autowired
    private PedidosService service;

 // --- NUEVO ENDPOINT ---
    @GetMapping("/historial")
    public ResponseEntity<List<HistorialDTO>> verHistorial() {
        return ResponseEntity.ok(service.listarHistorial());
    }
    
    @GetMapping
    public List<Pedidos> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody VentaDTO venta) {
        int resultado = service.registrarVenta(venta);
        
        if (resultado == 1) {
            return ResponseEntity.ok("Venta registrada con éxito");
        } else {
            return ResponseEntity.badRequest().body("Error al registrar la venta");
        }
    }
}