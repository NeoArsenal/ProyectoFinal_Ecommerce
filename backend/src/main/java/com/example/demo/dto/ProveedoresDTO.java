package com.example.demo.dto;

public class ProveedoresDTO {

    private Integer id;
    private String empresa;
    private String contacto;
    private String telefono;

    public ProveedoresDTO() {
    }

    // Getters y Setters manuales
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}