package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AlmacenesDTO;
import com.example.demo.model.Almacenes;
import com.example.demo.repository.AlmacenesRepository;

@Service
public class AlmacenesServiceImp implements AlmacenesService {

    @Autowired
    private AlmacenesRepository repository;

    @Override
    public List<AlmacenesDTO> listar() {
        List<Almacenes> lista = repository.findAll();
        List<AlmacenesDTO> dtos = new ArrayList<>();
        for (Almacenes a : lista) {
            dtos.add(convertir(a));
        }
        return dtos;
    }

    @Override
    public Optional<AlmacenesDTO> buscar(Integer id) {
        return repository.findById(id).map(this::convertir);
    }

    @Override
    public int save(Almacenes a) {
        return repository.save(a) != null ? 1 : 0;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private AlmacenesDTO convertir(Almacenes a) {
        AlmacenesDTO dto = new AlmacenesDTO();
        dto.setId(a.getId());
        dto.setNombre(a.getNombre());
        dto.setUbicacion(a.getUbicacion());
        return dto;
    }
}
