package com.hermandadproject.gestionhermandades.model.dto;

import com.hermandadproject.gestionhermandades.model.enums.CategoriaMovimientoEconomico;
import com.hermandadproject.gestionhermandades.model.enums.TipoMovimientoEconomico;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record MovimientoEconomicoHermandadCreateDto(
        UUID uuidHermandad,
        @NotNull TipoMovimientoEconomico tipoMovimiento,
        @NotNull CategoriaMovimientoEconomico categoria,
        @NotBlank @Size(max = 200) String concepto,
        @Size(max = 800) String descripcion,
        @NotNull @DecimalMin(value = "0.01") BigDecimal importe,
        LocalDate fechaMovimiento
) {
}

