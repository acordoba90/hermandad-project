package com.hermandadproject.gestionhermandades.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "carisma_hermandad")
public class CarismaHermandadEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID uuid;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 800)
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private Integer orden;

    @Column(name = "prestigio_base", nullable = false)
    private Integer prestigioBase;

    @Column(name = "popularidad_base", nullable = false)
    private Integer popularidadBase;

    @Column(name = "solemnidad_base", nullable = false)
    private Integer solemnidadBase;

    @Column(name = "devocion_base", nullable = false)
    private Integer devocionBase;

    @Column(name = "impacto_economico_base", nullable = false)
    private Integer impactoEconomicoBase;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getPrestigioBase() {
        return prestigioBase;
    }

    public void setPrestigioBase(Integer prestigioBase) {
        this.prestigioBase = prestigioBase;
    }

    public Integer getPopularidadBase() {
        return popularidadBase;
    }

    public void setPopularidadBase(Integer popularidadBase) {
        this.popularidadBase = popularidadBase;
    }

    public Integer getSolemnidadBase() {
        return solemnidadBase;
    }

    public void setSolemnidadBase(Integer solemnidadBase) {
        this.solemnidadBase = solemnidadBase;
    }

    public Integer getDevocionBase() {
        return devocionBase;
    }

    public void setDevocionBase(Integer devocionBase) {
        this.devocionBase = devocionBase;
    }

    public Integer getImpactoEconomicoBase() {
        return impactoEconomicoBase;
    }

    public void setImpactoEconomicoBase(Integer impactoEconomicoBase) {
        this.impactoEconomicoBase = impactoEconomicoBase;
    }

    @PrePersist
    void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (activo == null) {
            activo = true;
        }
    }
}

