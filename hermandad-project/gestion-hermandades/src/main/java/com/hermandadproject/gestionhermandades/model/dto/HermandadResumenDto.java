package com.hermandadproject.gestionhermandades.model.dto;

import com.hermandadproject.gestionhermandades.model.enums.EstadoHermandad;

import java.util.UUID;

public record HermandadResumenDto(
        UUID id,
        UUID idUsuario,
        String nombre,
        String ciudad,
        EstadoHermandad estado,
        Integer prestigio,
        Integer popularidad,
        Integer devocion,
        Integer solemnidad,
        TipoHermandadResumenDto tipoHermandad,
        CarismaHermandadResumenDto carismaPrincipal
) {
}

