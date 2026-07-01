package com.hermandadproject.gestionhermandades.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TipoHermandadCreateDto(
        @NotBlank @Size(max = 50) String codigo,
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 500) String descripcion,
        @NotNull Integer nivel,
        @NotNull Boolean activo,
        @NotNull Boolean puedeEstacionPenitencia,
        @NotNull Boolean puedeCultosExternos,
        @NotNull Boolean puedeTenerSedeCanonica,
        @NotNull Boolean puedeTenerPaso,
        @NotNull @Min(0) Integer prestigioBase,
        @NotNull Integer orden
) {
}

