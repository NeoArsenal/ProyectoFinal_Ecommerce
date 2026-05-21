package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DetallesUsuario;

@Repository
public interface DetallesUsuarioRepository extends JpaRepository<DetallesUsuario, Integer> {
    // Permite verificar si un DNI ya está registrado en el sistema
    Optional<DetallesUsuario> findByDni(String dni);
}
