package com.hermandadproject.gestionpasos.model.dto;

import com.hermandadproject.gestionpasos.model.enums.SlotTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record PasoSlotResponse(
        UUID id,
        String codigo,
        String claveHueco,
        SlotTypeEnum tipo,
        Integer posicionX,
        Integer posicionY,
        Integer indiceZ,
        BigDecimal escalaPorDefecto,
        BigDecimal rotacionPorDefecto,
        Boolean activo
) {
}
