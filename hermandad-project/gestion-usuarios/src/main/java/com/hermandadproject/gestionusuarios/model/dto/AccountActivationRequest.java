package com.hermandadproject.gestionusuarios.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountActivationRequest(
        @NotBlank String token
) {
}
