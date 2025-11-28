package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.repository.PedidosRepository;
import com.example.demo.repository.ProductosRepository;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    @Autowired private PedidosRepository pedidosRepo;
    @Autowired private ProductosRepository productosRepo;

    @GetMapping("/resumen")
    public ResponseEntity<DashboardDTO> obtenerResumen() {
        // Consultas JPQL
        Double total = pedidosRepo.sumTotalVentas();
        Long cantidad = pedidosRepo.count(); // count() es nativo de JpaRepository
        Long bajoStock = productosRepo.countProductosBajoStock();

        DashboardDTO dto = new DashboardDTO(total, cantidad, bajoStock);
        return ResponseEntity.ok(dto);
    }
}
