package com.hermandadproject.gestionrecompensas.mapper;

import com.hermandadproject.gestionrecompensas.model.dto.RewardPackResponse;
import com.hermandadproject.gestionrecompensas.model.entity.SobreRecompensaEntity;
import org.springframework.stereotype.Component;

@Component
public class RewardPackMapper {

    public RewardPackResponse toResponse(SobreRecompensaEntity entity) {
        return new RewardPackResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecio(),
                entity.getCantidadRecompensas(),
                entity.getRarezaMinima(),
                entity.getActivo()
        );
    }
}
