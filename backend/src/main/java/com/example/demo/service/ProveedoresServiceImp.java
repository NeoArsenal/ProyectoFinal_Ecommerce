package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProveedoresDTO;
import com.example.demo.model.Proveedores;
import com.example.demo.repository.ProveedoresRepository;

@Service
public class ProveedoresServiceImp implements ProveedoresService {

    @Autowired
    private ProveedoresRepository repository;

    @Override
    public List<ProveedoresDTO> listar() {
        List<Proveedores> lista = repository.findAll();
        List<ProveedoresDTO> listaDto = new ArrayList<>();
        
        for (Proveedores p : lista) {
            listaDto.add(convertirADTO(p));
        }
        return listaDto;
    }

    @Override
    public Optional<ProveedoresDTO> buscar(Integer id) {
        return repository.findById(id).map(this::convertirADTO);
    }

    @Override
    public int save(Proveedores p) {
        int res = 0;
        Proveedores saved = repository.save(p);
        if (saved != null) {
            res = 1;
        }
        return res;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Proveedores> listarId(Integer id) {
        return repository.findById(id);
    }

    // Método privado para convertir Entidad -> DTO
    private ProveedoresDTO convertirADTO(Proveedores p) {
        ProveedoresDTO dto = new ProveedoresDTO();
        dto.setId(p.getId());
        dto.setEmpresa(p.getEmpresa());
        dto.setContacto(p.getContacto());
        dto.setTelefono(p.getTelefono());
        return dto;
    }
}
