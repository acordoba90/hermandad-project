package com.hermandadproject.gestionhermandades.model.dto;

import java.util.UUID;

public class TipoHermandadResumenDto {

    private UUID uuid;
    private String codigo;
    private String nombre;
    private Integer nivel;

    public TipoHermandadResumenDto() {
    }

    public TipoHermandadResumenDto(UUID uuid, String codigo, String nombre, Integer nivel) {
        this.uuid = uuid;
        this.codigo = codigo;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
}

