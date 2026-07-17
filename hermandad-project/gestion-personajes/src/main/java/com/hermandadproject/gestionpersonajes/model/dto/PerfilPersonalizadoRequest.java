package com.hermandadproject.gestionpersonajes.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Datos preparados para crear perfiles personalizados sin arquetipo de origen.
 */
public record PerfilPersonalizadoRequest(
        @NotNull @Min(0) @Max(100) Integer liderazgo,
        @NotNull @Min(0) @Max(100) Integer carisma,
        @NotNull @Min(0) @Max(100) Integer diplomacia,
        @NotNull @Min(0) @Max(100) Integer organizacion,
        @NotNull @Min(0) @Max(100) Integer comunicacion,
        @NotNull @Min(0) @Max(100) Integer influencia,
        @NotNull @Min(0) @Max(100) Integer conocimientoCofrade,
        @NotNull @Min(0) @Max(100) Integer protocolo,
        @NotNull @Min(0) @Max(100) Integer devocion,
        @NotNull @Min(0) @Max(100) Integer disciplina,
        @NotNull @Min(0) @Max(100) Integer empatia,
        @NotNull @Min(0) @Max(100) Integer lealtad,
        @NotNull @Min(0) @Max(100) Integer integridad,
        @NotNull @Min(0) @Max(100) Integer ambicion,
        @NotNull @Min(0) @Max(100) Integer conflictividad,
        @NotNull @Min(0) @Max(100) Integer popularidad,
        @NotNull @Min(0) @Max(100) Integer reputacion
) {
}
