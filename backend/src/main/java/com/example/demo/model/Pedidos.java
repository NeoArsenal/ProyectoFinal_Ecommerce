package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime fecha;
    private Double total;
    
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("pedido")
    private List<DetallesPedido> detalles = new ArrayList<>();

    // --- NUEVAS RELACIONES (Esto es lo que te falta) ---
    
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagos pago;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Envios envio;

    public Pedidos() {
        this.fecha = LocalDateTime.now();
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetallesPedido detalle) {
        detalles.add(detalle);
        detalle.setPedido(this);
    }

    // --- GETTERS Y SETTERS ---
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    
    public List<DetallesPedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallesPedido> detalles) { 
        this.detalles = detalles;
        for(DetallesPedido d : detalles){ d.setPedido(this); }
    }
    
    // Estos son los que pide el error:
    public Pagos getPago() { return pago; }
    public void setPago(Pagos pago) { this.pago = pago; }
    
    public Envios getEnvio() { return envio; }
    public void setEnvio(Envios envio) { this.envio = envio; }
}
