package com.example.demo.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuariosDTO {
    
    private Integer id;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido (ej: usuario@mail.com)")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    // --- CAMPOS DE REGISTRO COMPLETO (Actividad N°5) ---
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
    @Pattern(
        regexp = "^[a-zA-Z\u00C0-\u024F ]+$",
        message = "El nombre solo puede contener letras y espacios (sin números ni caracteres especiales)"
    )
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 30, message = "El apellido debe tener entre 3 y 30 caracteres")
    @Pattern(
        regexp = "^[a-zA-Z\u00C0-\u024F ]+$",
        message = "El apellido solo puede contener letras y espacios (sin números ni caracteres especiales)"
    )
    private String apellido;

    @Pattern(
        regexp = "^[MF]$",
        message = "El género debe ser M o F"
    )
    private String genero;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(
        regexp = "^\\d{8}$",
        message = "El DNI debe tener exactamente 8 dígitos numéricos"
    )
    private String dni;
    // -----------------------------------------------------
    
    // --- ESTE CAMPO ES EL QUE FALTABA PARA EL FRONTEND ---
    private List<String> rolesNombres; 

    public UsuariosDTO() {}

    public UsuariosDTO(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    // --- GETTERS Y SETTERS ---
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // --- GETTERS Y SETTERS PARA NUEVOS CAMPOS ---
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    // --- GETTERS Y SETTERS PARA LA LISTA DE ROLES ---
    public List<String> getRolesNombres() { return rolesNombres; }
    public void setRolesNombres(List<String> rolesNombres) { this.rolesNombres = rolesNombres; }
}