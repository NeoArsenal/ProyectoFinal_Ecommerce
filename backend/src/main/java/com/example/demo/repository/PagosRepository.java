package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pagos;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {
}
