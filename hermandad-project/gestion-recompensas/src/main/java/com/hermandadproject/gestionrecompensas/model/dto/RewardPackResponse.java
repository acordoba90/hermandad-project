package com.hermandadproject.gestionrecompensas.model.dto;

import com.hermandadproject.gestionrecompensas.model.enums.RewardRarityEnum;

import java.util.UUID;

public record RewardPackResponse(
        UUID id,
        String codigo,
        String nombre,
        String descripcion,
        Integer precio,
        Integer cantidadRecompensas,
        RewardRarityEnum rarezaMinima,
        Boolean activo
) {
}
