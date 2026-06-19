package com.example.demo.dto;

public class AuthResponseDTO {
    private String token;
    private UsuariosDTO usuario;

    public AuthResponseDTO(String token, UsuariosDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }
}
