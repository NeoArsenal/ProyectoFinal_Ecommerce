package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.UsuariosDTO;
import com.example.demo.model.DetallesUsuario;
import com.example.demo.model.Usuarios;
import com.example.demo.repository.DetallesUsuarioRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UsuariosRepository;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceImpTest {

    @Mock
    private UsuariosRepository repository;

    @Mock
    private DetallesUsuarioRepository detallesRepo;

    @Mock
    private RolesRepository rolesRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuariosServiceImp usuariosService;

    private Usuarios usuario;
    private UsuariosDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuarios();
        usuario.setId(1);
        usuario.setEmail("test@test.com");
        usuario.setPassword("$2a$10$encodedpassword");

        usuarioDTO = new UsuariosDTO();
        usuarioDTO.setEmail("new@test.com");
        usuarioDTO.setPassword("password123");
        usuarioDTO.setNombre("Juan");
        usuarioDTO.setApellido("Perez");
    }

    @Test
    void testLoginExitoso() {
        when(repository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("password123", usuario.getPassword())).thenReturn(true);

        UsuariosDTO result = usuariosService.login("test@test.com", "password123");

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
        assertEquals(1, result.getId());
    }

    @Test
    void testLoginFallido() {
        when(repository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrong", usuario.getPassword())).thenReturn(false);

        UsuariosDTO result = usuariosService.login("test@test.com", "wrong");

        assertNull(result);
    }

    @Test
    void testRegistrarExitoso() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_pass");
        
        Usuarios guardado = new Usuarios();
        guardado.setId(2);
        guardado.setEmail(usuarioDTO.getEmail());
        guardado.setPassword("hashed_pass");
        
        when(repository.save(any(Usuarios.class))).thenReturn(guardado);
        when(detallesRepo.save(any(DetallesUsuario.class))).thenReturn(new DetallesUsuario());

        UsuariosDTO result = usuariosService.registrar(usuarioDTO);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals(usuarioDTO.getEmail(), result.getEmail());
        verify(detallesRepo, times(1)).save(any(DetallesUsuario.class));
    }
    
    @Test
    void testRegistrarFallaEmailDuplicado() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuariosService.registrar(usuarioDTO);
        });
        
        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(repository, never()).save(any(Usuarios.class));
    }

    @Test
    void testLoginExitosoConBcrypt2b() {
        usuario.setPassword("$2b$10$encodedpassword");
        when(repository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("password123", usuario.getPassword())).thenReturn(true);

        UsuariosDTO result = usuariosService.login("test@test.com", "password123");

        assertNotNull(result);
    }

    @Test
    void testLoginExitosoLegacyMigracion() {
        usuario.setPassword("password123");
        when(repository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("password123")).thenReturn("$2a$10$newhash");

        UsuariosDTO result = usuariosService.login("test@test.com", "password123");

        assertNotNull(result);
        verify(repository, times(1)).save(usuario);
        assertEquals("$2a$10$newhash", usuario.getPassword());
    }

    @Test
    void testLoginFallidoLegacy() {
        usuario.setPassword("legacyPass");
        when(repository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        UsuariosDTO result = usuariosService.login("test@test.com", "wrong");

        assertNull(result);
    }

    @Test
    void testRegistrarFallaDniDuplicado() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        usuarioDTO.setDni("12345678");
        when(detallesRepo.findByDni("12345678")).thenReturn(Optional.of(new DetallesUsuario()));
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuariosService.registrar(usuarioDTO);
        });
        
        assertEquals("El DNI ya está registrado en el sistema", exception.getMessage());
    }
}
