package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {

    // JPQL: Usa nombres de las Clases Java (Productos, categoria)
    @Query("SELECT p FROM Productos p JOIN p.categoria c ORDER BY p.id")
    List<Productos> listarProductosJPQL();

    // SQL Nativo: Usa nombres de tablas de BD (productos, categorias)
    @Query(value = "SELECT p.* FROM productos p " +
                   "INNER JOIN categorias c ON c.id = p.categoria_id " +
                   "ORDER BY p.id", nativeQuery = true)
    List<Productos> listarProductosSQL();
    
 // Consulta JPQL para contar productos con stock menor a 5
    @Query("SELECT COUNT(p) FROM Productos p WHERE p.stock < 5")
    Long countProductosBajoStock();
}

