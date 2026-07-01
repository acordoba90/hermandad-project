package com.hermandadproject.gestionhermandades.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EconomiaHermandadDto(
        UUID uuid,
        UUID uuidHermandad,
        BigDecimal saldoActual,
        BigDecimal ingresosMensuales,
        BigDecimal gastosMensuales,
        BigDecimal deudaActual,
        BigDecimal patrimonioEstimado,
        Integer nivelEstabilidadEconomica,
        LocalDate fechaUltimaActualizacion
) {
}

