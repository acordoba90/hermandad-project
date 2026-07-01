package com.hermandadproject.gestionusuarios.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserProfileCreateRequest(
        @NotNull UUID idUsuario,
        @NotBlank @Size(max = 50) String alias,
        @Size(max = 255) String urlAvatar
) {
}
