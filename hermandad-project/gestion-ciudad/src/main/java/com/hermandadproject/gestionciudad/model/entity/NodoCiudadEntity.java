package com.hermandadproject.gestionciudad.model.entity;

import com.hermandadproject.gestionciudad.model.enums.AnchuraViaEnum;
import com.hermandadproject.gestionciudad.model.enums.TipoNodoCiudadEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
        name = "nodos_ciudad",
        uniqueConstraints = @UniqueConstraint(name = "uk_nodos_ciudad_mapa_codigo", columnNames = {"mapa_ciudad_id", "codigo"})
)
public class NodoCiudadEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapa_ciudad_id", nullable = false)
    private MapaCiudadEntity mapaCiudad;

    @Column(nullable = false, length = 100)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoNodoCiudadEnum tipo;

    @Column(name = "posicion_x", nullable = false)
    private Integer posicionX;

    @Column(name = "posicion_y", nullable = false)
    private Integer posicionY;

    @Column(length = 150)
    private String distrito;

    @Enumerated(EnumType.STRING)
    @Column(name = "anchura_via", nullable = false, length = 50)
    private AnchuraViaEnum anchuraVia;

    @Column(name = "nivel_publico", nullable = false)
    private Integer nivelPublico;

    @Column(nullable = false)
    private Integer dificultad;

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

    public MapaCiudadEntity getMapaCiudad() {
        return mapaCiudad;
    }

    public void setMapaCiudad(MapaCiudadEntity mapaCiudad) {
        this.mapaCiudad = mapaCiudad;
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

    public TipoNodoCiudadEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoNodoCiudadEnum tipo) {
        this.tipo = tipo;
    }

    public Integer getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(Integer posicionX) {
        this.posicionX = posicionX;
    }

    public Integer getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(Integer posicionY) {
        this.posicionY = posicionY;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public AnchuraViaEnum getAnchuraVia() {
        return anchuraVia;
    }

    public void setAnchuraVia(AnchuraViaEnum anchuraVia) {
        this.anchuraVia = anchuraVia;
    }

    public Integer getNivelPublico() {
        return nivelPublico;
    }

    public void setNivelPublico(Integer nivelPublico) {
        this.nivelPublico = nivelPublico;
    }

    public Integer getDificultad() {
        return dificultad;
    }

    public void setDificultad(Integer dificultad) {
        this.dificultad = dificultad;
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
        Instant ahora = Instant.now();
        fechaCreacion = ahora;
        fechaActualizacion = ahora;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
