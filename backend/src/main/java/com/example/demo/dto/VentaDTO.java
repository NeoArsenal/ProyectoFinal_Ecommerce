package com.example.demo.dto;

import java.util.List;

public class VentaDTO {
    
    private Integer usuarioId;
    
    // --- NUEVOS CAMPOS ---
    private String metodoPago; // "Tarjeta", "Efectivo"
    private Integer metodoEnvioId; // ID del método (1=Express, 2=Normal)
    // ---------------------

    private List<ItemVentaDTO> items;

    // Getters y Setters
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    
    public Integer getMetodoEnvioId() { return metodoEnvioId; }
    public void setMetodoEnvioId(Integer metodoEnvioId) { this.metodoEnvioId = metodoEnvioId; }

    public List<ItemVentaDTO> getItems() { return items; }
    public void setItems(List<ItemVentaDTO> items) { this.items = items; }

    public static class ItemVentaDTO {
        private Integer productoId;
        private Integer cantidad;
        private Double precio;

        public Integer getProductoId() { return productoId; }
        public void setProductoId(Integer productoId) { this.productoId = productoId; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        public Double getPrecio() { return precio; }
        public void setPrecio(Double precio) { this.precio = precio; }
    }
}