package com.hermandadproject.gestionhermandades.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "economia_hermandad")
public class EconomiaHermandadEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "char(36)")
    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_hermandad", nullable = false, unique = true)
    private HermandadEntity hermandad;

    @Column(name = "saldo_actual", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldoActual;

    @Column(name = "ingresos_mensuales", nullable = false, precision = 12, scale = 2)
    private BigDecimal ingresosMensuales;

    @Column(name = "gastos_mensuales", nullable = false, precision = 12, scale = 2)
    private BigDecimal gastosMensuales;

    @Column(name = "deuda_actual", nullable = false, precision = 12, scale = 2)
    private BigDecimal deudaActual;

    @Column(name = "patrimonio_estimado", nullable = false, precision = 12, scale = 2)
    private BigDecimal patrimonioEstimado;

    @Column(name = "nivel_estabilidad_economica", nullable = false)
    private Integer nivelEstabilidadEconomica;

    @Column(name = "fecha_ultima_actualizacion")
    private LocalDate fechaUltimaActualizacion;

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

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public BigDecimal getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(BigDecimal ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public BigDecimal getGastosMensuales() {
        return gastosMensuales;
    }

    public void setGastosMensuales(BigDecimal gastosMensuales) {
        this.gastosMensuales = gastosMensuales;
    }

    public BigDecimal getDeudaActual() {
        return deudaActual;
    }

    public void setDeudaActual(BigDecimal deudaActual) {
        this.deudaActual = deudaActual;
    }

    public BigDecimal getPatrimonioEstimado() {
        return patrimonioEstimado;
    }

    public void setPatrimonioEstimado(BigDecimal patrimonioEstimado) {
        this.patrimonioEstimado = patrimonioEstimado;
    }

    public Integer getNivelEstabilidadEconomica() {
        return nivelEstabilidadEconomica;
    }

    public void setNivelEstabilidadEconomica(Integer nivelEstabilidadEconomica) {
        this.nivelEstabilidadEconomica = nivelEstabilidadEconomica;
    }

    public LocalDate getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(LocalDate fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    @PrePersist
    void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (saldoActual == null) {
            saldoActual = BigDecimal.ZERO;
        }
        if (ingresosMensuales == null) {
            ingresosMensuales = BigDecimal.ZERO;
        }
        if (gastosMensuales == null) {
            gastosMensuales = BigDecimal.ZERO;
        }
        if (deudaActual == null) {
            deudaActual = BigDecimal.ZERO;
        }
        if (patrimonioEstimado == null) {
            patrimonioEstimado = BigDecimal.ZERO;
        }
        if (nivelEstabilidadEconomica == null) {
            nivelEstabilidadEconomica = 5;
        }
        if (fechaUltimaActualizacion == null) {
            fechaUltimaActualizacion = LocalDate.now();
        }
    }

    @PreUpdate
    void preUpdate() {
        fechaUltimaActualizacion = LocalDate.now();
    }
}

