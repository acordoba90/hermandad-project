package com.hermandadproject.gestionhermandades.model.dto;

import java.util.UUID;

public record TipoHermandadDto(
        UUID uuid,
        String codigo,
        String nombre,
        String descripcion,
        Integer nivel,
        Boolean activo,
        Boolean puedeEstacionPenitencia,
        Boolean puedeCultosExternos,
        Boolean puedeTenerSedeCanonica,
        Boolean puedeTenerPaso,
        Integer prestigioBase,
        Integer orden,
        TipoHermandadCaracteristicasResponse caracteristicas
) {
}
