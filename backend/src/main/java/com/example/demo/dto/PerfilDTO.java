package com.example.demo.dto;

public class PerfilDTO {
    private String direccion;
    private String telefono;
    private String dni;
    
    // Campo opcional para mostrar el email (solo lectura)
    private String email;

    public PerfilDTO() {}

    public PerfilDTO(String direccion, String telefono, String dni, String email) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.dni = dni;
        this.email = email;
    }

    // Getters y Setters
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
