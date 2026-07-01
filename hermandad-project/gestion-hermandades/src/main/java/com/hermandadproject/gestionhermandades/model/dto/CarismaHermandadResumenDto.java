package com.hermandadproject.gestionhermandades.model.dto;

import java.util.UUID;

public class CarismaHermandadResumenDto {

    private UUID uuid;
    private String codigo;
    private String nombre;

    public CarismaHermandadResumenDto() {
    }

    public CarismaHermandadResumenDto(UUID uuid, String codigo, String nombre) {
        this.uuid = uuid;
        this.codigo = codigo;
        this.nombre = nombre;
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
}

