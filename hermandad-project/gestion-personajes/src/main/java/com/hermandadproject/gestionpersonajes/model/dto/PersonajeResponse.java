package com.hermandadproject.gestionpersonajes.model.dto;

import com.hermandadproject.gestionpersonajes.model.enums.GenderEnum;

import java.time.Instant;
import java.util.UUID;

public record PersonajeResponse(
        UUID id,
        String codigo,
        UUID usuarioId,
        UUID avatarId,
        UUID colectivoId,
        String colectivoCode,
        String colectivoName,
        UUID rolPersonajeId,
        String rolPersonajeCodigo,
        String rolPersonajeNombre,
        String nombre,
        String apellidos,
        Integer edad,
        GenderEnum genero,
        String origen,
        String profesion,
        String descripcion,
        String biografia,
        String motivacion,
        String tipoPersonaje,
        Boolean personalizado,
        String urlAvatar,
        Boolean activo,
        Instant fechaCreacion,
        Instant fechaActualizacion,
        PerfilPersonajeResponse perfil
) {
}
