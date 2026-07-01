package com.hermandadproject.gestionrecompensas.model.dto;

import com.hermandadproject.gestionrecompensas.model.enums.RewardItemTypeEnum;
import com.hermandadproject.gestionrecompensas.model.enums.RewardRarityEnum;

import java.util.UUID;

public record RewardPackPoolItemResponse(
        UUID id,
        UUID idSobreRecompensa,
        RewardItemTypeEnum tipoElemento,
        UUID idElemento,
        String codigoElemento,
        String nombreElemento,
        RewardRarityEnum rareza,
        Integer peso,
        Integer cantidad,
        Boolean activo
) {
}
