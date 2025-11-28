package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RolesDTO;
import com.example.demo.model.Roles;
import com.example.demo.repository.RolesRepository;

@Service
public class RolesServiceImp implements RolesService {

    @Autowired
    private RolesRepository repository;

    @Override
    public List<RolesDTO> listar() {
        List<Roles> lista = repository.findAll();
        List<RolesDTO> dtos = new ArrayList<>();
        for (Roles r : lista) {
            RolesDTO dto = new RolesDTO();
            dto.setId(r.getId());
            dto.setNombre(r.getNombre());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public int guardar(RolesDTO dto) {
        Roles rol = new Roles();
        if (dto.getId() != null) rol.setId(dto.getId());
        rol.setNombre(dto.getNombre());
        return repository.save(rol) != null ? 1 : 0;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
