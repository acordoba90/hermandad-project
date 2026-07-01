package com.hermandadproject.gestionhermandades.model.dto;

import com.hermandadproject.gestionhermandades.model.enums.CategoriaMovimientoEconomico;
import com.hermandadproject.gestionhermandades.model.enums.TipoMovimientoEconomico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record MovimientoEconomicoHermandadDto(
        UUID uuid,
        UUID uuidHermandad,
        TipoMovimientoEconomico tipoMovimiento,
        CategoriaMovimientoEconomico categoria,
        String concepto,
        String descripcion,
        BigDecimal importe,
        LocalDate fechaMovimiento,
        LocalDateTime fechaRegistro
) {
}

