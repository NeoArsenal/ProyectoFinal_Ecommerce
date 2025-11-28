package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MetodoEnvioDTO;
import com.example.demo.model.MetodosEnvio;
import com.example.demo.repository.MetodosEnvioRepository;

@Service
public class MetodosEnvioServiceImp implements MetodosEnvioService {

    @Autowired
    private MetodosEnvioRepository repository;

    @Override
    public List<MetodoEnvioDTO> listar() {
        List<MetodosEnvio> lista = repository.findAll();
        List<MetodoEnvioDTO> dtos = new ArrayList<>();
        for (MetodosEnvio m : lista) {
            MetodoEnvioDTO dto = new MetodoEnvioDTO();
            dto.setId(m.getId());
            dto.setNombre(m.getNombre());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public int guardar(MetodoEnvioDTO dto) {
        MetodosEnvio metodo = new MetodosEnvio();
        metodo.setId(dto.getId()); // Si viene ID, actualiza
        metodo.setNombre(dto.getNombre());
        return repository.save(metodo) != null ? 1 : 0;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
