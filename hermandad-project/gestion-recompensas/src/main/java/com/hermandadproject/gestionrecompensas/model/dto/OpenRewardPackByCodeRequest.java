package com.hermandadproject.gestionrecompensas.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OpenRewardPackByCodeRequest(
        @NotNull UUID idHermandad,
        @NotBlank String codigoSobreRecompensa
) {
}
