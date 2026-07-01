package com.hermandadproject.gestionrecompensas.model.entity;

import com.hermandadproject.gestionrecompensas.model.enums.RewardRarityEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sobres_recompensa")
public class SobreRecompensaEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Integer precio;

    @Column(name = "cantidad_recompensas", nullable = false)
    private Integer cantidadRecompensas;

    @Enumerated(EnumType.STRING)
    @Column(name = "rareza_minima", nullable = false, length = 30)
    private RewardRarityEnum rarezaMinima;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getCantidadRecompensas() {
        return cantidadRecompensas;
    }

    public void setCantidadRecompensas(Integer cantidadRecompensas) {
        this.cantidadRecompensas = cantidadRecompensas;
    }

    public RewardRarityEnum getRarezaMinima() {
        return rarezaMinima;
    }

    public void setRarezaMinima(RewardRarityEnum rarezaMinima) {
        this.rarezaMinima = rarezaMinima;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }

    @PrePersist
    void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (activo == null) {
            activo = true;
        }
        Instant now = Instant.now();
        fechaCreacion = now;
        fechaActualizacion = now;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
