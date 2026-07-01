package com.hermandadproject.gestionpersonajes.model.dto;

import com.hermandadproject.gestionpersonajes.model.enums.GenderEnum;

import java.time.Instant;
import java.util.UUID;

public record PersonajeResponse(
        UUID id,
        String codigo,
        UUID colectivoId,
        String colectivoCode,
        String colectivoName,
        String nombre,
        String apellidos,
        Integer edad,
        GenderEnum genero,
        String origen,
        String descripcion,
        String urlAvatar,
        Boolean activo,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
