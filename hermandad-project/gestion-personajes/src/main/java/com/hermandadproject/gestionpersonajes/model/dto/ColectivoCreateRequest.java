package com.hermandadproject.gestionpersonajes.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ColectivoCreateRequest(
        @NotBlank @Size(max = 100) String codigo,
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 500) String descripcion
) {
}
