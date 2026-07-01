package com.hermandadproject.gestionrecompensas.mapper;

import com.hermandadproject.gestionrecompensas.model.dto.RewardPackRewardResponse;
import com.hermandadproject.gestionrecompensas.model.entity.ElementoPoolSobreRecompensaEntity;
import com.hermandadproject.gestionrecompensas.model.entity.RecompensaSobreEntity;
import org.springframework.stereotype.Component;

@Component
public class RewardPackRewardMapper {

    public RecompensaSobreEntity toEntity(ElementoPoolSobreRecompensaEntity poolItem) {
        RecompensaSobreEntity entity = new RecompensaSobreEntity();
        entity.setTipoElemento(poolItem.getTipoElemento());
        entity.setIdElemento(poolItem.getIdElemento());
        entity.setCodigoElemento(poolItem.getCodigoElemento());
        entity.setNombreElemento(poolItem.getNombreElemento());
        entity.setRareza(poolItem.getRareza());
        entity.setCantidad(poolItem.getCantidad());
        return entity;
    }

    public RewardPackRewardResponse toResponse(RecompensaSobreEntity entity) {
        return new RewardPackRewardResponse(
                entity.getId(),
                entity.getTipoElemento(),
                entity.getIdElemento(),
                entity.getCodigoElemento(),
                entity.getNombreElemento(),
                entity.getRareza(),
                entity.getCantidad()
        );
    }
}
