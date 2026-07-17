package com.hermandadproject.gestionpersonajes.model.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO de salida para consultar roles de personaje disponibles en el catalogo.
 */
public record RolPersonajeResponse(
        UUID id,
        UUID colectivoId,
        String colectivoCodigo,
        String colectivoNombre,
        String codigo,
        String nombre,
        String descripcion,
        Boolean activo,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
