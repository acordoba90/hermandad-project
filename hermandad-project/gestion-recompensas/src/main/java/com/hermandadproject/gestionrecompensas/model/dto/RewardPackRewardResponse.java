package com.hermandadproject.gestionrecompensas.model.dto;

import com.hermandadproject.gestionrecompensas.model.enums.RewardItemTypeEnum;
import com.hermandadproject.gestionrecompensas.model.enums.RewardRarityEnum;

import java.util.UUID;

public record RewardPackRewardResponse(
        UUID id,
        RewardItemTypeEnum tipoElemento,
        UUID idElemento,
        String codigoElemento,
        String nombreElemento,
        RewardRarityEnum rareza,
        Integer cantidad
) {
}
