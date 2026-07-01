package com.hermandadproject.gestionusuarios.model.dto;

import com.hermandadproject.gestionusuarios.model.enums.UserRoleEnum;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String nombreUsuario,
        String correoElectronico,
        UserRoleEnum rol,
        Instant fechaCreacion,
        Instant fechaActualizacion,
        Instant vigenteDesde,
        Instant vigenteHasta
) {
}
