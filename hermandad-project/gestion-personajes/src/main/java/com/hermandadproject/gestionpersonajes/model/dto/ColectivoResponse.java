package com.hermandadproject.gestionpersonajes.model.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Representacion de salida de un colectivo de personajes.
 */
public record ColectivoResponse(
        UUID id,
        String codigo,
        String nombre,
        String descripcion,
        Boolean activo,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
