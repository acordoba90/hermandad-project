package com.hermandadproject.gestionhermandades.model.dto;

import java.util.UUID;

public record CarismaHermandadDto(
        UUID uuid,
        String codigo,
        String nombre,
        String descripcion,
        Boolean activo,
        Integer orden,
        Integer prestigioBase,
        Integer popularidadBase,
        Integer solemnidadBase,
        Integer devocionBase,
        Integer impactoEconomicoBase
) {
}

