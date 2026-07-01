package com.hermandadproject.gestionusuarios.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserProfileUpdateRequest(
        @NotBlank @Size(max = 50) String alias,
        @Size(max = 255) String urlAvatar
) {
}
