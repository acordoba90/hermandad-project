package com.hermandadproject.gestionpasos.model.dto;

import com.hermandadproject.gestionpasos.model.enums.FigureTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record PasoFigureResponse(
        UUID id,
        String codigo,
        String nombre,
        String descripcion,
        FigureTypeEnum tipo,
        String urlRecurso,
        BigDecimal precio,
        Integer prestigioRequerido,
        Boolean activo
) {
}
