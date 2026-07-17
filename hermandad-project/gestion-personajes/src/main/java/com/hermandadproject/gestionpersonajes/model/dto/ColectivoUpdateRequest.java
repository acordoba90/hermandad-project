package com.hermandadproject.gestionpersonajes.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Datos modificables de un colectivo existente.
 */
public record ColectivoUpdateRequest(
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 500) String descripcion,
        @NotNull Boolean activo
) {
}
