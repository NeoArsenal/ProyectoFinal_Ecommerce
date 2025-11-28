package com.example.demo.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // El nombre del rol debe ser único (no pueden haber dos roles 'ADMIN')
    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // Ejemplos: "ROLE_ADMIN", "ROLE_USER"

    // Relación inversa Muchos a Muchos con Usuarios
    // "mappedBy" apunta al atributo 'roles' que está dentro de la clase Usuarios.java
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // Evita que al pedir un rol, te traiga todos los usuarios infinitamente
    private List<Usuarios> usuarios;

    public Roles() {
    }

    // Constructor útil para crear roles rápido
    public Roles(String nombre) {
        this.nombre = nombre;
    }

    // --- GETTERS Y SETTERS ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Roles other = (Roles) obj;
        return Objects.equals(id, other.id);
    }
}
