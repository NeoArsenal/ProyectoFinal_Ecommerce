package com.example.demo.dto;

public class DashboardDTO {
    private Double totalVentas;
    private Long cantidadPedidos;
    private Long productosBajoStock;

    public DashboardDTO(Double totalVentas, Long cantidadPedidos, Long productosBajoStock) {
        this.totalVentas = totalVentas != null ? totalVentas : 0.0;
        this.cantidadPedidos = cantidadPedidos != null ? cantidadPedidos : 0L;
        this.productosBajoStock = productosBajoStock != null ? productosBajoStock : 0L;
    }

    // Getters y Setters
    public Double getTotalVentas() { return totalVentas; }
    public void setTotalVentas(Double totalVentas) { this.totalVentas = totalVentas; }
    
    public Long getCantidadPedidos() { return cantidadPedidos; }
    public void setCantidadPedidos(Long cantidadPedidos) { this.cantidadPedidos = cantidadPedidos; }
    
    public Long getProductosBajoStock() { return productosBajoStock; }
    public void setProductosBajoStock(Long productosBajoStock) { this.productosBajoStock = productosBajoStock; }
}
