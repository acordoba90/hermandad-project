package com.hermandadproject.gestionciudad.model.dto;

import java.util.UUID;

public record ConexionCiudadResponse(
        UUID id,
        UUID mapaCiudadId,
        UUID nodoOrigenId,
        UUID nodoDestinoId,
        Integer distanciaMetros,
        Integer minutosEstimados,
        Integer dificultad,
        Boolean activa
) {
}
