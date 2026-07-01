package com.hermandadproject.gestionciudad.model.dto;

import java.util.UUID;

public record CarreraOficialResponse(
        UUID id,
        UUID ciudadId,
        UUID mapaCiudadId,
        UUID nodoEntradaId,
        UUID nodoSalidaId,
        String nombre,
        String descripcion,
        Integer minutosEstimados,
        Boolean activa
) {
}
