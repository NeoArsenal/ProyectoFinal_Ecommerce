package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.MetodosEnvio;

@Repository
public interface MetodosEnvioRepository extends JpaRepository<MetodosEnvio, Integer> {
}
