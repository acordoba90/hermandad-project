package com.hermandadproject.gestionpersonajes.model.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Respuesta con el perfil actual y evolutivo de un personaje.
 */
public record PerfilPersonajeResponse(
        UUID id,
        UUID personajeId,
        UUID arquetipoOrigenId,
        String arquetipoOrigenCodigo,
        String arquetipoOrigenNombre,
        Integer nivel,
        Long experiencia,
        Integer puntosDesarrollo,
        Integer liderazgo,
        Integer carisma,
        Integer diplomacia,
        Integer organizacion,
        Integer comunicacion,
        Integer influencia,
        Integer conocimientoCofrade,
        Integer protocolo,
        Integer devocion,
        Integer disciplina,
        Integer empatia,
        Integer lealtad,
        Integer integridad,
        Integer ambicion,
        Integer conflictividad,
        Integer popularidad,
        Integer reputacion,
        Boolean activo,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
