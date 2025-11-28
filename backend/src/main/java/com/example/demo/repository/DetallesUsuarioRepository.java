package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DetallesUsuario;

@Repository
public interface DetallesUsuarioRepository extends JpaRepository<DetallesUsuario, Integer> {
}
