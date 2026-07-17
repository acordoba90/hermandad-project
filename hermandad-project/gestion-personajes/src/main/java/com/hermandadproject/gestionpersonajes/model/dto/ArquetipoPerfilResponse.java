package com.hermandadproject.gestionpersonajes.model.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Respuesta con los datos visibles y atributos base de un arquetipo de perfil.
 */
public record ArquetipoPerfilResponse(
        UUID id,
        String codigo,
        String nombre,
        String descripcion,
        Integer liderazgoBase,
        Integer carismaBase,
        Integer diplomaciaBase,
        Integer organizacionBase,
        Integer comunicacionBase,
        Integer influenciaBase,
        Integer conocimientoCofradeBase,
        Integer protocoloBase,
        Integer devocionBase,
        Integer disciplinaBase,
        Integer empatiaBase,
        Integer lealtadBase,
        Integer integridadBase,
        Integer ambicionBase,
        Integer conflictividadBase,
        Integer popularidadBase,
        Integer reputacionBase,
        Boolean activo,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
