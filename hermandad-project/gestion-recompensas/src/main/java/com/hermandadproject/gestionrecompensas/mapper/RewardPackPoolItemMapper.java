package com.hermandadproject.gestionrecompensas.mapper;

import com.hermandadproject.gestionrecompensas.model.dto.RewardPackPoolItemResponse;
import com.hermandadproject.gestionrecompensas.model.entity.ElementoPoolSobreRecompensaEntity;
import org.springframework.stereotype.Component;

@Component
public class RewardPackPoolItemMapper {

    public RewardPackPoolItemResponse toResponse(ElementoPoolSobreRecompensaEntity entity) {
        return new RewardPackPoolItemResponse(
                entity.getId(),
                entity.getSobreRecompensa().getId(),
                entity.getTipoElemento(),
                entity.getIdElemento(),
                entity.getCodigoElemento(),
                entity.getNombreElemento(),
                entity.getRareza(),
                entity.getPeso(),
                entity.getCantidad(),
                entity.getActivo()
        );
    }
}
