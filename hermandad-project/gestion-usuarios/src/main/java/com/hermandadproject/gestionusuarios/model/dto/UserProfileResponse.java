package com.hermandadproject.gestionusuarios.model.dto;

import java.time.Instant;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        UUID idUsuario,
        String alias,
        String urlAvatar,
        Integer nivel,
        Integer experiencia,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
