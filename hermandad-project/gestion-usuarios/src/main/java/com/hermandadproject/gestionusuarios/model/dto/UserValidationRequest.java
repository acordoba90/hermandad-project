package com.hermandadproject.gestionusuarios.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserValidationRequest(
        @NotBlank @Email @Size(max = 150) String correoElectronico,
        @NotBlank @Size(min = 8, max = 72) String password
) {
}
