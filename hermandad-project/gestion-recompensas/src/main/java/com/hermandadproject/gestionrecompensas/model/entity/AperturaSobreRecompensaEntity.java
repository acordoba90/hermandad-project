package com.hermandadproject.gestionrecompensas.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "aperturas_sobre_recompensa")
public class AperturaSobreRecompensaEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id_hermandad", nullable = false, columnDefinition = "char(36)")
    private UUID idHermandad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sobre_recompensa_id", nullable = false, columnDefinition = "char(36)")
    private SobreRecompensaEntity sobreRecompensa;

    @Column(name = "fecha_apertura", nullable = false)
    private Instant fechaApertura;

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

    public UUID getIdHermandad() {
        return idHermandad;
    }

    public void setIdHermandad(UUID idHermandad) {
        this.idHermandad = idHermandad;
    }

    public SobreRecompensaEntity getSobreRecompensa() {
        return sobreRecompensa;
    }

    public void setSobreRecompensa(SobreRecompensaEntity sobreRecompensa) {
        this.sobreRecompensa = sobreRecompensa;
    }

    public Instant getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Instant fechaApertura) {
        this.fechaApertura = fechaApertura;
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
        if (fechaApertura == null) {
            fechaApertura = Instant.now();
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
