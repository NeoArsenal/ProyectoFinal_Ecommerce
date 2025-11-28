package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "envios")
public class Envios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_tracking", length = 100)
    private String numeroTracking;

    @Column(length = 50)
    private String estado; // "Enviado", "En Tránsito", "Entregado"

    // Relación 1 a 1: Un envío corresponde a un pedido específico
    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedidos pedido;

    // Relación Muchos a 1: Muchos envíos pueden usar el método "Express"
    @ManyToOne
    @JoinColumn(name = "metodo_envio_id")
    private MetodosEnvio metodoEnvio;

    public Envios() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNumeroTracking() { return numeroTracking; }
    public void setNumeroTracking(String numeroTracking) { this.numeroTracking = numeroTracking; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Pedidos getPedido() { return pedido; }
    public void setPedido(Pedidos pedido) { this.pedido = pedido; }
    public MetodosEnvio getMetodoEnvio() { return metodoEnvio; }
    public void setMetodoEnvio(MetodosEnvio metodoEnvio) { this.metodoEnvio = metodoEnvio; }
}
