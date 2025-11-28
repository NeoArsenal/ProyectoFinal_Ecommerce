package com.example.demo.dto;

import java.util.List;

public class UsuarioRolDTO {
    private Integer usuarioId;
    private List<Integer> rolesIds; // IDs de los roles a asignar

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public List<Integer> getRolesIds() { return rolesIds; }
    public void setRolesIds(List<Integer> rolesIds) { this.rolesIds = rolesIds; }
}
