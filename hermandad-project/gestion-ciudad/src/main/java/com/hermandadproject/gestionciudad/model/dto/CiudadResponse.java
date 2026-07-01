package com.hermandadproject.gestionciudad.model.dto;

import java.time.Instant;
import java.util.UUID;

public record CiudadResponse(
        UUID id,
        String codigo,
        String nombre,
        String descripcion,
        Boolean activa,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
