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

        // --- VALIDACIÓN 1: Email duplicado ---
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // --- VALIDACIÓN 2: DNI duplicado (Caso de prueba #7) ---
        if (dto.getDni() != null && detallesRepo.findByDni(dto.getDni()).isPresent()) {
            throw new RuntimeException("El DNI ya está registrado en el sistema");
        }

        // --- GUARDAR USUARIO BASE ---
        Usuarios nuevo = new Usuarios();
        nuevo.setEmail(dto.getEmail());
        nuevo.setPassword(dto.getPassword());
        Usuarios guardado = repository.save(nuevo);

        // --- GUARDAR DETALLES COMPLETOS (Actividad N°5: caso 9) ---
        DetallesUsuario detalles = new DetallesUsuario();
        detalles.setUsuario(guardado);
        detalles.setNombre(dto.getNombre());
        detalles.setApellido(dto.getApellido());
        detalles.setGenero(dto.getGenero());
        detalles.setDni(dto.getDni());
        detallesRepo.save(detalles);

        // --- RESPUESTA ---
        UsuariosDTO respuesta = new UsuariosDTO(guardado.getId(), guardado.getEmail());
        respuesta.setNombre(dto.getNombre());
        respuesta.setApellido(dto.getApellido());
        return respuesta;
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

        PerfilDTO perfil = new PerfilDTO();
        perfil.setEmail(u.getEmail());

        if (d != null) {
            perfil.setNombre(d.getNombre());
            perfil.setApellido(d.getApellido());
            perfil.setGenero(d.getGenero());
            perfil.setDireccion(d.getDireccion());
            perfil.setTelefono(d.getTelefono());
            perfil.setDni(d.getDni());
        }

        return perfil;
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

        // Actualizar todos los campos incluyendo los nuevos
        if (dto.getNombre()    != null) detalles.setNombre(dto.getNombre());
        if (dto.getApellido()  != null) detalles.setApellido(dto.getApellido());
        if (dto.getGenero()    != null) detalles.setGenero(dto.getGenero());
        if (dto.getDireccion() != null) detalles.setDireccion(dto.getDireccion());
        if (dto.getTelefono()  != null) detalles.setTelefono(dto.getTelefono());
        if (dto.getDni()       != null) detalles.setDni(dto.getDni());

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
