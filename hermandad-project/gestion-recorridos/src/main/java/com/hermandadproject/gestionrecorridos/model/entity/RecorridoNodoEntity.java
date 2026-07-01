package com.hermandadproject.gestionrecorridos.model.entity;

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
        name = "recorrido_nodos",
        uniqueConstraints = @UniqueConstraint(name = "uk_recorrido_nodos_recorrido_orden", columnNames = {"recorrido_id", "orden"})
)
public class RecorridoNodoEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorrido_id", nullable = false)
    private RecorridoEntity recorrido;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "nodo_ciudad_id", nullable = false, columnDefinition = "char(36)")
    private UUID idNodoCiudad;

    @Column(name = "codigo_nodo", nullable = false, length = 100)
    private String codigoNodo;

    @Column(name = "nombre_nodo", nullable = false, length = 150)
    private String nombreNodo;

    @Column(nullable = false)
    private Integer orden;

    @Column(name = "minutos_desde_anterior", nullable = false)
    private Integer minutosDesdeAnterior;

    @Column(name = "distancia_desde_anterior_metros", nullable = false)
    private Integer distanciaDesdeAnteriorMetros;

    @Column(name = "dificultad_tramo", nullable = false)
    private Integer dificultadTramo;

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

    public RecorridoEntity getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(RecorridoEntity recorrido) {
        this.recorrido = recorrido;
    }

    public UUID getIdNodoCiudad() {
        return idNodoCiudad;
    }

    public void setIdNodoCiudad(UUID idNodoCiudad) {
        this.idNodoCiudad = idNodoCiudad;
    }

    public String getCodigoNodo() {
        return codigoNodo;
    }

    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    public String getNombreNodo() {
        return nombreNodo;
    }

    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getMinutosDesdeAnterior() {
        return minutosDesdeAnterior;
    }

    public void setMinutosDesdeAnterior(Integer minutosDesdeAnterior) {
        this.minutosDesdeAnterior = minutosDesdeAnterior;
    }

    public Integer getDistanciaDesdeAnteriorMetros() {
        return distanciaDesdeAnteriorMetros;
    }

    public void setDistanciaDesdeAnteriorMetros(Integer distanciaDesdeAnteriorMetros) {
        this.distanciaDesdeAnteriorMetros = distanciaDesdeAnteriorMetros;
    }

    public Integer getDificultadTramo() {
        return dificultadTramo;
    }

    public void setDificultadTramo(Integer dificultadTramo) {
        this.dificultadTramo = dificultadTramo;
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
        Instant ahora = Instant.now();
        fechaCreacion = ahora;
        fechaActualizacion = ahora;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
