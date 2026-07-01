package com.hermandadproject.gestionhermandades.model.entity;

import com.hermandadproject.gestionhermandades.model.enums.CategoriaMovimientoEconomico;
import com.hermandadproject.gestionhermandades.model.enums.TipoMovimientoEconomico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movimiento_economico_hermandad")
public class MovimientoEconomicoHermandadEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_hermandad", nullable = false)
    private HermandadEntity hermandad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private TipoMovimientoEconomico tipoMovimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CategoriaMovimientoEconomico categoria;

    @Column(nullable = false, length = 200)
    private String concepto;

    @Column(length = 800)
    private String descripcion;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal importe;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDate fechaMovimiento;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public HermandadEntity getHermandad() {
        return hermandad;
    }

    public void setHermandad(HermandadEntity hermandad) {
        this.hermandad = hermandad;
    }

    public TipoMovimientoEconomico getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimientoEconomico tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public CategoriaMovimientoEconomico getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaMovimientoEconomico categoria) {
        this.categoria = categoria;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @PrePersist
    void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
        if (fechaMovimiento == null) {
            fechaMovimiento = LocalDate.now();
        }
    }
}

