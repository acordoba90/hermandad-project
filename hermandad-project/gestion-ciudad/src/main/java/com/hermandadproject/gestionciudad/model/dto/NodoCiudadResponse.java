package com.hermandadproject.gestionciudad.model.dto;

import com.hermandadproject.gestionciudad.model.enums.AnchuraViaEnum;
import com.hermandadproject.gestionciudad.model.enums.TipoNodoCiudadEnum;

import java.util.UUID;

public record NodoCiudadResponse(
        UUID id,
        UUID mapaCiudadId,
        String codigo,
        String nombre,
        TipoNodoCiudadEnum tipo,
        Integer posicionX,
        Integer posicionY,
        String distrito,
        AnchuraViaEnum anchuraVia,
        Integer nivelPublico,
        Integer dificultad,
        Boolean activo
) {
}
