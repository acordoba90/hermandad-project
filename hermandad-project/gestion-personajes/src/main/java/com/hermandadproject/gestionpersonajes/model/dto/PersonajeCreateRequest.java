package com.hermandadproject.gestionpersonajes.model.dto;

import com.hermandadproject.gestionpersonajes.model.enums.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PersonajeCreateRequest(
        @NotBlank @Size(max = 100) String codigo,
        UUID usuarioId,
        UUID avatarId,
        @NotNull UUID colectivoId,
        @NotNull UUID rolPersonajeId,
        @NotBlank @Size(max = 100) String nombre,
        @Size(max = 150) String apellidos,
        @Positive Integer edad,
        @NotNull GenderEnum genero,
        @Size(max = 150) String origen,
        @Size(max = 150) String profesion,
        @Size(max = 500) String descripcion,
        String biografia,
        String motivacion,
        @Size(max = 100) String tipoPersonaje,
        Boolean personalizado,
        UUID arquetipoPerfilId,
        @Size(max = 255) String urlAvatar
) {
}
