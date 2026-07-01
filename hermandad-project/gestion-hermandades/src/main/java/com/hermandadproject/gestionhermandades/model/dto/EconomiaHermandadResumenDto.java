package com.hermandadproject.gestionhermandades.model.dto;

import java.math.BigDecimal;

public class EconomiaHermandadResumenDto {

    private BigDecimal saldoActual;
    private BigDecimal deudaActual;
    private BigDecimal patrimonioEstimado;
    private Integer nivelEstabilidadEconomica;

    public EconomiaHermandadResumenDto() {
    }

    public EconomiaHermandadResumenDto(BigDecimal saldoActual, BigDecimal deudaActual, BigDecimal patrimonioEstimado, Integer nivelEstabilidadEconomica) {
        this.saldoActual = saldoActual;
        this.deudaActual = deudaActual;
        this.patrimonioEstimado = patrimonioEstimado;
        this.nivelEstabilidadEconomica = nivelEstabilidadEconomica;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
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
}

