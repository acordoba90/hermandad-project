package com.hermandadproject.gestioninventario.model.entity;

import com.hermandadproject.gestioninventario.model.enums.InventoryItemTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
        name = "elementos_inventario",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_inventory_items_hermandad_type_code",
                columnNames = {"id_hermandad", "tipo_elemento", "codigo_elemento"}
        )
)
public class ElementoInventarioEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id_hermandad", nullable = false, columnDefinition = "char(36)")
    private UUID idHermandad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_elemento", nullable = false, length = 50)
    private InventoryItemTypeEnum tipoElemento;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id_elemento", columnDefinition = "char(36)")
    private UUID idElemento;

    @Column(name = "codigo_elemento", nullable = false, length = 100)
    private String codigoElemento;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "fecha_adquisicion", nullable = false)
    private Instant fechaAdquisicion;

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

    public InventoryItemTypeEnum getTipoElemento() {
        return tipoElemento;
    }

    public void setTipoElemento(InventoryItemTypeEnum tipoElemento) {
        this.tipoElemento = tipoElemento;
    }

    public UUID getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(UUID idElemento) {
        this.idElemento = idElemento;
    }

    public String getCodigoElemento() {
        return codigoElemento;
    }

    public void setCodigoElemento(String codigoElemento) {
        this.codigoElemento = codigoElemento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Instant getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Instant fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
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
        if (fechaAdquisicion == null) {
            fechaAdquisicion = Instant.now();
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
