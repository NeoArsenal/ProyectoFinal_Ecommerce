package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventarios")
public class Inventarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer cantidad;

    // Relación: Muchos registros de inventario pertenecen a un Producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Productos producto;

    // Relación: Muchos registros de inventario están en un Almacén
    @ManyToOne
    @JoinColumn(name = "almacen_id")
    private Almacenes almacen;

    public Inventarios() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Productos getProducto() { return producto; }
    public void setProducto(Productos producto) { this.producto = producto; }
    public Almacenes getAlmacen() { return almacen; }
    public void setAlmacen(Almacenes almacen) { this.almacen = almacen; }
}
