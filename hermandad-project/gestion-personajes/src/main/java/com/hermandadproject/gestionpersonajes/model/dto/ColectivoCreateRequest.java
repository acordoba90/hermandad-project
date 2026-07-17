package com.hermandadproject.gestionpersonajes.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Datos necesarios para crear un colectivo de personajes.
 */
public record ColectivoCreateRequest(
        @NotBlank @Size(max = 100) @Pattern(regexp = "^[A-Z0-9_]+$") String codigo,
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 500) String descripcion
) {
}
