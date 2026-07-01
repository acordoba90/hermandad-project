package com.hermandadproject.gestionpasos.model.entity;

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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "figuras_colocadas",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_placed_figures_hermandad_template_slot",
                columnNames = {"id_hermandad", "plantilla_paso_id", "hueco_paso_id"}
        )
)
public class FiguraColocadaEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id_hermandad", nullable = false, columnDefinition = "char(36)")
    private UUID idHermandad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantilla_paso_id", nullable = false, columnDefinition = "char(36)")
    private PlantillaPasoEntity plantillaPaso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hueco_paso_id", nullable = false, columnDefinition = "char(36)")
    private HuecoPasoEntity huecoPaso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "figura_paso_id", nullable = false, columnDefinition = "char(36)")
    private FiguraPasoEntity figuraPaso;

    @Column(name = "desplazamiento_x", nullable = false)
    private Integer desplazamientoX;

    @Column(name = "desplazamiento_y", nullable = false)
    private Integer desplazamientoY;

    @Column(nullable = false, precision = 6, scale = 3)
    private BigDecimal escala;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal rotacion;

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

    public PlantillaPasoEntity getPlantillaPaso() {
        return plantillaPaso;
    }

    public void setPlantillaPaso(PlantillaPasoEntity plantillaPaso) {
        this.plantillaPaso = plantillaPaso;
    }

    public HuecoPasoEntity getHuecoPaso() {
        return huecoPaso;
    }

    public void setHuecoPaso(HuecoPasoEntity huecoPaso) {
        this.huecoPaso = huecoPaso;
    }

    public FiguraPasoEntity getFiguraPaso() {
        return figuraPaso;
    }

    public void setFiguraPaso(FiguraPasoEntity figuraPaso) {
        this.figuraPaso = figuraPaso;
    }

    public Integer getDesplazamientoX() {
        return desplazamientoX;
    }

    public void setDesplazamientoX(Integer desplazamientoX) {
        this.desplazamientoX = desplazamientoX;
    }

    public Integer getDesplazamientoY() {
        return desplazamientoY;
    }

    public void setDesplazamientoY(Integer desplazamientoY) {
        this.desplazamientoY = desplazamientoY;
    }

    public BigDecimal getEscala() {
        return escala;
    }

    public void setEscala(BigDecimal escala) {
        this.escala = escala;
    }

    public BigDecimal getRotacion() {
        return rotacion;
    }

    public void setRotacion(BigDecimal rotacion) {
        this.rotacion = rotacion;
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
        Instant now = Instant.now();
        fechaCreacion = now;
        fechaActualizacion = now;
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
