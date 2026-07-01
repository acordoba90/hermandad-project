package com.hermandadproject.gestionpersonajes.model.dto;

import com.hermandadproject.gestionpersonajes.model.enums.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PersonajeUpdateRequest(
        @NotNull UUID colectivoId,
        @NotBlank @Size(max = 100) String nombre,
        @Size(max = 150) String apellidos,
        @Positive Integer edad,
        @NotNull GenderEnum genero,
        @Size(max = 150) String origen,
        @Size(max = 500) String descripcion,
        @Size(max = 255) String urlAvatar,
        @NotNull Boolean activo
) {
}
