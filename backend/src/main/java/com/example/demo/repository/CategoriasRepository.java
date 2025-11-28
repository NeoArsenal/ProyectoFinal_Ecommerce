package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Categorias;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Integer> {
    // Aquí podrías agregar métodos como findByNombre(String nombre)...
}
