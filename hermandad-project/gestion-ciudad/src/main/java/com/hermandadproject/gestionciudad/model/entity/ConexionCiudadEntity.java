package com.hermandadproject.gestionciudad.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "conexiones_ciudad",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_conexiones_ciudad_mapa_origen_destino",
                columnNames = {"mapa_ciudad_id", "nodo_origen_id", "nodo_destino_id"}
        )
)
public class ConexionCiudadEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapa_ciudad_id", nullable = false)
    private MapaCiudadEntity mapaCiudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nodo_origen_id", nullable = false)
    private NodoCiudadEntity nodoOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nodo_destino_id", nullable = false)
    private NodoCiudadEntity nodoDestino;

    @Column(name = "distancia_metros", nullable = false)
    private Integer distanciaMetros;

    @Column(name = "minutos_estimados", nullable = false)
    private Integer minutosEstimados;

    @Column(nullable = false)
    private Integer dificultad;

    @Column(nullable = false)
    private Boolean activa;

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

    public MapaCiudadEntity getMapaCiudad() {
        return mapaCiudad;
    }

    public void setMapaCiudad(MapaCiudadEntity mapaCiudad) {
        this.mapaCiudad = mapaCiudad;
    }

    public NodoCiudadEntity getNodoOrigen() {
        return nodoOrigen;
    }

    public void setNodoOrigen(NodoCiudadEntity nodoOrigen) {
        this.nodoOrigen = nodoOrigen;
    }

    public NodoCiudadEntity getNodoDestino() {
        return nodoDestino;
    }

    public void setNodoDestino(NodoCiudadEntity nodoDestino) {
        this.nodoDestino = nodoDestino;
    }

    public Integer getDistanciaMetros() {
        return distanciaMetros;
    }

    public void setDistanciaMetros(Integer distanciaMetros) {
        this.distanciaMetros = distanciaMetros;
    }

    public Integer getMinutosEstimados() {
        return minutosEstimados;
    }

    public void setMinutosEstimados(Integer minutosEstimados) {
        this.minutosEstimados = minutosEstimados;
    }

    public Integer getDificultad() {
        return dificultad;
    }

    public void setDificultad(Integer dificultad) {
        this.dificultad = dificultad;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
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
        if (activa == null) {
            activa = true;
        }
        Instant ahora = Instant.now();
        fechaCreacion = ahora;
        fechaActualizacion = ahora;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
