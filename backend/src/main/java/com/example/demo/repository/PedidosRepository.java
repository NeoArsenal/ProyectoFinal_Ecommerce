package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pedidos;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Integer> {
	// Consulta JPQL para sumar la columna 'total' de todos los pedidos
    @Query("SELECT SUM(p.total) FROM Pedidos p")
    Double sumTotalVentas();
}
