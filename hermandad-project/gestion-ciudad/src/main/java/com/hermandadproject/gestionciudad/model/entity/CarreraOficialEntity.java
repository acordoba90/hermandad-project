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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "carreras_oficiales")
public class CarreraOficialEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id", nullable = false)
    private CiudadEntity ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapa_ciudad_id", nullable = false)
    private MapaCiudadEntity mapaCiudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nodo_entrada_id", nullable = false)
    private NodoCiudadEntity nodoEntrada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nodo_salida_id", nullable = false)
    private NodoCiudadEntity nodoSalida;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "minutos_estimados", nullable = false)
    private Integer minutosEstimados;

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

    public CiudadEntity getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadEntity ciudad) {
        this.ciudad = ciudad;
    }

    public MapaCiudadEntity getMapaCiudad() {
        return mapaCiudad;
    }

    public void setMapaCiudad(MapaCiudadEntity mapaCiudad) {
        this.mapaCiudad = mapaCiudad;
    }

    public NodoCiudadEntity getNodoEntrada() {
        return nodoEntrada;
    }

    public void setNodoEntrada(NodoCiudadEntity nodoEntrada) {
        this.nodoEntrada = nodoEntrada;
    }

    public NodoCiudadEntity getNodoSalida() {
        return nodoSalida;
    }

    public void setNodoSalida(NodoCiudadEntity nodoSalida) {
        this.nodoSalida = nodoSalida;
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

    public Integer getMinutosEstimados() {
        return minutosEstimados;
    }

    public void setMinutosEstimados(Integer minutosEstimados) {
        this.minutosEstimados = minutosEstimados;
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
