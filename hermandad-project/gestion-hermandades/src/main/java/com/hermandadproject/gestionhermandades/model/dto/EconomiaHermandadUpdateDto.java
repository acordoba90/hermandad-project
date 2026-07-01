package com.hermandadproject.gestionhermandades.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record EconomiaHermandadUpdateDto(
        @DecimalMin("0.00") BigDecimal ingresosMensuales,
        @DecimalMin("0.00") BigDecimal gastosMensuales,
        @DecimalMin("0.00") BigDecimal deudaActual,
        @DecimalMin("0.00") BigDecimal patrimonioEstimado,
        @Min(1) @Max(10) Integer nivelEstabilidadEconomica
) {
}

