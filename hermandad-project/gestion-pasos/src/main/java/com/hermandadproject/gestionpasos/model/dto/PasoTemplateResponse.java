package com.hermandadproject.gestionpasos.model.dto;

import com.hermandadproject.gestionpasos.model.enums.PasoTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record PasoTemplateResponse(
        UUID id,
        String codigo,
        String nombre,
        String descripcion,
        PasoTypeEnum tipo,
        String urlRecurso,
        BigDecimal precio,
        Integer prestigioRequerido,
        Boolean activo
) {
}
