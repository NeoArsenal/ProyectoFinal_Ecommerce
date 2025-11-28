package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoriasDTO;
import com.example.demo.model.Categorias;
import com.example.demo.repository.CategoriasRepository;

@Service
public class CategoriasServiceImp implements CategoriasService {

    @Autowired
    private CategoriasRepository repository;

    @Override
    public List<CategoriasDTO> listar() {
        List<Categorias> lista = repository.findAll();
        List<CategoriasDTO> listaDto = new ArrayList<>();
        
        for (Categorias c : lista) {
            listaDto.add(convertirADTO(c));
        }
        return listaDto;
    }

    @Override
    public Optional<CategoriasDTO> buscar(Integer id) {
        return repository.findById(id).map(this::convertirADTO);
    }

    @Override
    public int save(Categorias c) {
        int band = 0;
        Categorias saved = repository.save(c);
        if (saved != null) {
            band = 1;
        }
        return band;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
    
    @Override
    public Optional<Categorias> listarId(Integer id) {
        return repository.findById(id);
    }

    // Método privado para convertir Entidad -> DTO manualmente
    private CategoriasDTO convertirADTO(Categorias c) {
        CategoriasDTO dto = new CategoriasDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        return dto;
    }
}
