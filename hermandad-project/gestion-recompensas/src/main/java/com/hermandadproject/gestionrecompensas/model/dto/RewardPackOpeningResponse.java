package com.hermandadproject.gestionrecompensas.model.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RewardPackOpeningResponse(
        UUID id,
        UUID idHermandad,
        UUID idSobreRecompensa,
        String codigoSobreRecompensa,
        String nombreSobreRecompensa,
        Instant fechaApertura,
        List<RewardPackRewardResponse> recompensas
) {
}
