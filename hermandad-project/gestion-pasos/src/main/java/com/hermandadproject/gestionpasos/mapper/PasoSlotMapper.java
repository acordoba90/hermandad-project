package com.hermandadproject.gestionpasos.mapper;

import com.hermandadproject.gestionpasos.model.dto.PasoSlotResponse;
import com.hermandadproject.gestionpasos.model.entity.HuecoPasoEntity;
import org.springframework.stereotype.Component;

@Component
public class PasoSlotMapper {

    public PasoSlotResponse toResponse(HuecoPasoEntity entity) {
        return new PasoSlotResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getClaveHueco(),
                entity.getTipo(),
                entity.getPosicionX(),
                entity.getPosicionY(),
                entity.getIndiceZ(),
                entity.getEscalaPorDefecto(),
                entity.getRotacionPorDefecto(),
                entity.getActivo()
        );
    }
}
