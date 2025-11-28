package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Inventarios;

@Repository
public interface InventariosRepository extends JpaRepository<Inventarios, Integer> {
    // Aquí podrías buscar stock por producto y almacén
    // Optional<Inventarios> findByProductoIdAndAlmacenId(Integer prodId, Integer almId);
	 Optional<Inventarios> findByProductoIdAndAlmacenId(Integer productoId, Integer almacenId);
}
