package com.example.demo.dto;

import java.util.List;

public class ProductosDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Integer stock;
    
    private Integer categoriaId;
    private String categoriaNombre;

    // --- NUEVOS CAMPOS PARA N:M ---
    private List<Integer> proveedorIds; // Para recibir del formulario (IDs)
    private List<String> proveedorNombres; // Para mostrar en la tabla (Nombres)

    public ProductosDTO() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
    
    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }

    public List<Integer> getProveedorIds() { return proveedorIds; }
    public void setProveedorIds(List<Integer> proveedorIds) { this.proveedorIds = proveedorIds; }

    public List<String> getProveedorNombres() { return proveedorNombres; }
    public void setProveedorNombres(List<String> proveedorNombres) { this.proveedorNombres = proveedorNombres; }
}
