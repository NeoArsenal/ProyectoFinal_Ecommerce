package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PerfilDTO;
import com.example.demo.dto.UsuariosDTO;
import com.example.demo.model.DetallesUsuario;
import com.example.demo.model.Roles;
import com.example.demo.model.Usuarios;
import com.example.demo.repository.DetallesUsuarioRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UsuariosRepository;

@Service
public class UsuariosServiceImp implements UsuariosService {

    @Autowired
    private UsuariosRepository repository;
    
    @Autowired
    private DetallesUsuarioRepository detallesRepo;
    
    @Autowired
    private RolesRepository rolesRepo;

    @Override
    public UsuariosDTO login(String email, String password) {
        Optional<Usuarios> usuarioOpt = repository.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuarios u = usuarioOpt.get();
            // Validación simple (en producción usar BCrypt)
            if (u.getPassword().equals(password)) {
                return new UsuariosDTO(u.getId(), u.getEmail());
            }
        }
        return null; // Login fallido
    }

    @Override
    public UsuariosDTO registrar(UsuariosDTO dto) {
        // Verificamos si ya existe el correo
        if(repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuarios nuevo = new Usuarios();
        nuevo.setEmail(dto.getEmail());
        nuevo.setPassword(dto.getPassword());
        
        Usuarios guardado = repository.save(nuevo);
        return new UsuariosDTO(guardado.getId(), guardado.getEmail());
    }

    @Override
    public List<UsuariosDTO> listar() {
        List<Usuarios> lista = repository.findAll();
        List<UsuariosDTO> listaDto = new ArrayList<>();
        
        for (Usuarios u : lista) {
            UsuariosDTO dto = new UsuariosDTO(u.getId(), u.getEmail());
            
            // --- Mapear Nombres de Roles para el Frontend ---
            if (u.getRoles() != null) {
                List<String> nombres = u.getRoles().stream()
                                        .map(Roles::getNombre)
                                        .collect(Collectors.toList());
                dto.setRolesNombres(nombres);
            }
            // -----------------------------------------------
            
            listaDto.add(dto);
        }
        return listaDto;
    }

    @Override
    public PerfilDTO obtenerPerfil(Integer usuarioId) {
        Optional<Usuarios> usuarioOpt = repository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) return null;

        Usuarios u = usuarioOpt.get();
        DetallesUsuario d = u.getDetalles();

        if (d == null) {
            return new PerfilDTO("", "", "", u.getEmail());
        }

        return new PerfilDTO(d.getDireccion(), d.getTelefono(), d.getDni(), u.getEmail());
    }

    @Override
    public int guardarPerfil(Integer usuarioId, PerfilDTO dto) {
        Optional<Usuarios> usuarioOpt = repository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) return 0;

        Usuarios u = usuarioOpt.get();
        DetallesUsuario detalles = u.getDetalles();

        if (detalles == null) {
            detalles = new DetallesUsuario();
            detalles.setUsuario(u);
        }

        detalles.setDireccion(dto.getDireccion());
        detalles.setTelefono(dto.getTelefono());
        detalles.setDni(dto.getDni());

        detallesRepo.save(detalles);
        u.setDetalles(detalles);
        repository.save(u);

        return 1;
    }

    @Override
    public void asignarRoles(Integer usuarioId, List<Integer> rolesIds) {
        Optional<Usuarios> uOpt = repository.findById(usuarioId);
        if (uOpt.isPresent()) {
            Usuarios usuario = uOpt.get();
            
            // Buscamos los roles reales por sus IDs
            List<Roles> roles = rolesRepo.findAllById(rolesIds);
            
            // Asignamos y guardamos
            usuario.setRoles(roles);
            repository.save(usuario);
        }
    }

    @Override
    public List<Integer> obtenerRolesIds(Integer usuarioId) {
        Optional<Usuarios> uOpt = repository.findById(usuarioId);
        if (uOpt.isPresent()) {
            // Convertimos la lista de objetos Roles a una lista de IDs (Integers)
            return uOpt.get().getRoles().stream()
                    .map(Roles::getId)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
