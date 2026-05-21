package com.example.demo.dto;

public class PerfilDTO {
    private String nombre;
    private String apellido;
    private String genero;
    private String direccion;
    private String telefono;
    private String dni;

    // Campo solo lectura para mostrar en pantalla
    private String email;

    public PerfilDTO() {}

    public PerfilDTO(String direccion, String telefono, String dni, String email) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.dni = dni;
        this.email = email;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
