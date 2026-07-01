package com.hermandadproject.gestionciudad.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record IglesiaResponse(
        UUID id,
        UUID ciudadId,
        UUID nodoCiudadId,
        String codigo,
        String nombre,
        String descripcion,
        Integer capacidad,
        Integer prestigio,
        Boolean disponibleComoSede,
        Boolean construible,
        BigDecimal costeConstruccion,
        Integer mesesConstruccion,
        Boolean activa
) {
}
