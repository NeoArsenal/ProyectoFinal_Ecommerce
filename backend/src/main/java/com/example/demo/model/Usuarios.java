package com.example.demo.model;

import java.util.List;
import java.util.Objects;

// Importaciones EXPLÍCITAS para evitar errores de "cannot be resolved"
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; // <--- Esta es la que te faltaba reconocer
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;
    
    private String password;

    // --- RELACIÓN N:M CON ROLES ---
    // FetchType.EAGER: Al cargar el usuario, trae sus roles de inmediato (útil para seguridad)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles", // Nombre de la tabla intermedia que se creará sola
        // Usamos llaves { } para definir el array de columnas
        joinColumns = { @JoinColumn(name = "usuario_id") },
        inverseJoinColumns = { @JoinColumn(name = "rol_id") }
    )
    private List<Roles> roles;

    // --- RELACIÓN 1:1 CON DETALLES (Opcional) ---
    // Si quieres guardar dirección, teléfono, etc.
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private DetallesUsuario detalles;

    public Usuarios() {}
    
    // Constructor útil para pruebas rápidas
    public Usuarios(Integer id) {
        this.id = id;
    }

    // --- GETTERS Y SETTERS ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public DetallesUsuario getDetalles() {
        return detalles;
    }

    public void setDetalles(DetallesUsuario detalles) {
        this.detalles = detalles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuarios other = (Usuarios) obj;
        return Objects.equals(id, other.id);
    }
}