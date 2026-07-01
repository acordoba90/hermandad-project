package com.hermandadproject.gestionciudad.model.dto;

import java.util.UUID;

public record MapaCiudadResponse(
        UUID id,
        UUID ciudadId,
        String codigo,
        String nombre,
        String urlRecurso,
        Integer ancho,
        Integer alto,
        Boolean activo
) {
}
