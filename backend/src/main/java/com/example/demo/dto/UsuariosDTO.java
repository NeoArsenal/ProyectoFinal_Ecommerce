package com.example.demo.dto;

import java.util.List;

public class UsuariosDTO {
    
    private Integer id;
    private String email;
    private String password;
    
    // --- ESTE CAMPO ES EL QUE FALTABA ---
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

    // --- LOS MÉTODOS QUE TE DAN ERROR ---
    public List<String> getRolesNombres() { return rolesNombres; }
    public void setRolesNombres(List<String> rolesNombres) { this.rolesNombres = rolesNombres; }
}