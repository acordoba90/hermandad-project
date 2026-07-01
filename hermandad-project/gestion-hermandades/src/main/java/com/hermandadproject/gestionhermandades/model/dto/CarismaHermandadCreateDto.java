package com.hermandadproject.gestionhermandades.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarismaHermandadCreateDto(
        @NotBlank @Size(max = 50) String codigo,
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 800) String descripcion,
        @NotNull Boolean activo,
        @NotNull Integer orden,
        @NotNull @Min(0) Integer prestigioBase,
        @NotNull @Min(0) Integer popularidadBase,
        @NotNull @Min(0) Integer solemnidadBase,
        @NotNull @Min(0) Integer devocionBase,
        @NotNull @Min(0) Integer impactoEconomicoBase
) {
}

