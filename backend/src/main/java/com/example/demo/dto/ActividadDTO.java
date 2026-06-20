package com.example.demo.dto;

import java.time.LocalDateTime;

public class ActividadDTO implements Comparable<ActividadDTO> {
    private String texto;
    private String tiempo;
    private String icono;
    private String color;
    private LocalDateTime fechaRef;

    public ActividadDTO(String texto, LocalDateTime fechaRef, String icono, String color) {
        this.texto = texto;
        this.fechaRef = fechaRef;
        this.icono = icono;
        this.color = color;
        this.tiempo = calcularTiempoRelativo(fechaRef);
    }

    private String calcularTiempoRelativo(LocalDateTime fecha) {
        if (fecha == null) return "Hace tiempo";
        long segundos = java.time.Duration.between(fecha, LocalDateTime.now()).getSeconds();
        if (segundos < 0) segundos = 0;
        if (segundos < 60) return "Hace unos instantes";
        long minutos = segundos / 60;
        if (minutos < 60) return "Hace " + minutos + " min";
        long horas = minutos / 60;
        if (horas < 24) return "Hace " + horas + " h";
        long dias = horas / 24;
        return "Hace " + dias + " d";
    }

    // Getters y Setters
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    
    public String getTiempo() { return tiempo; }
    public void setTiempo(String tiempo) { this.tiempo = tiempo; }
    
    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public LocalDateTime getFechaRef() { return fechaRef; }
    public void setFechaRef(LocalDateTime fechaRef) { this.fechaRef = fechaRef; }

    @Override
    public int compareTo(ActividadDTO o) {
        if (this.fechaRef == null || o.fechaRef == null) return 0;
        return o.fechaRef.compareTo(this.fechaRef); // Descendente (más reciente primero)
    }
}
