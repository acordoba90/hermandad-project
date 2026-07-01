package com.hermandadproject.gestionrecompensas.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OpenRewardPackRequest(
        @NotNull UUID idHermandad,
        @NotNull UUID idSobreRecompensa
) {
}
