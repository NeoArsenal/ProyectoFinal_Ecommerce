package com.example.demo.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuariosDTO {
    
    private Integer id;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido (ej: usuario@mail.com)")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
    
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

    // --- GETTERS Y SETTERS PARA LA LISTA DE ROLES ---
    public List<String> getRolesNombres() { return rolesNombres; }
    public void setRolesNombres(List<String> rolesNombres) { this.rolesNombres = rolesNombres; }
}