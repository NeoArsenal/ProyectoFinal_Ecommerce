package com.example.demo.dto;

public class CategoriasDTO {

    private Integer id;
    private String nombre;
    private String descripcion;

    // Constructores (vacío es obligatorio)
    public CategoriasDTO() {}

    // Getters y Setters Manuales
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}