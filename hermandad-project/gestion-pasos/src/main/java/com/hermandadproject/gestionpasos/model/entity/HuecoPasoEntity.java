package com.hermandadproject.gestionpasos.model.entity;

import com.hermandadproject.gestionpasos.model.enums.SlotTypeEnum;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "huecos_paso",
        uniqueConstraints = @UniqueConstraint(name = "uk_paso_slots_template_slot_key", columnNames = {"plantilla_paso_id", "clave_hueco"})
)
public class HuecoPasoEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantilla_paso_id", nullable = false, columnDefinition = "char(36)")
    private PlantillaPasoEntity plantillaPaso;

    @Column(nullable = false, length = 100)
    private String codigo;

    @Column(name = "clave_hueco", nullable = false, length = 100)
    private String claveHueco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SlotTypeEnum tipo;

    @Column(name = "posicion_x", nullable = false)
    private Integer posicionX;

    @Column(name = "posicion_y", nullable = false)
    private Integer posicionY;

    @Column(name = "indice_z", nullable = false)
    private Integer indiceZ;

    @Column(name = "escala_por_defecto", nullable = false, precision = 6, scale = 3)
    private BigDecimal escalaPorDefecto;

    @Column(name = "rotacion_por_defecto", nullable = false, precision = 6, scale = 2)
    private BigDecimal rotacionPorDefecto;

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

    public PlantillaPasoEntity getPlantillaPaso() {
        return plantillaPaso;
    }

    public void setPlantillaPaso(PlantillaPasoEntity plantillaPaso) {
        this.plantillaPaso = plantillaPaso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getClaveHueco() {
        return claveHueco;
    }

    public void setClaveHueco(String claveHueco) {
        this.claveHueco = claveHueco;
    }

    public SlotTypeEnum getTipo() {
        return tipo;
    }

    public void setTipo(SlotTypeEnum tipo) {
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

    public Integer getIndiceZ() {
        return indiceZ;
    }

    public void setIndiceZ(Integer indiceZ) {
        this.indiceZ = indiceZ;
    }

    public BigDecimal getEscalaPorDefecto() {
        return escalaPorDefecto;
    }

    public void setEscalaPorDefecto(BigDecimal escalaPorDefecto) {
        this.escalaPorDefecto = escalaPorDefecto;
    }

    public BigDecimal getRotacionPorDefecto() {
        return rotacionPorDefecto;
    }

    public void setRotacionPorDefecto(BigDecimal rotacionPorDefecto) {
        this.rotacionPorDefecto = rotacionPorDefecto;
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
