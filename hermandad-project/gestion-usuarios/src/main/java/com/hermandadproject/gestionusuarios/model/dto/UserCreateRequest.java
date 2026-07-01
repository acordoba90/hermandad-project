package com.hermandadproject.gestionusuarios.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record UserCreateRequest(
        @NotBlank @Size(max = 100) String nombreUsuario,
        @NotBlank @Email @Size(max = 150) String correoElectronico,
        @NotBlank @Size(min = 8, max = 72) String password,
        Instant vigenteDesde,
        Instant vigenteHasta
) {
}
