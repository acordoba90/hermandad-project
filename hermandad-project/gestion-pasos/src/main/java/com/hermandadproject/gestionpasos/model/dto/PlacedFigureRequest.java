package com.hermandadproject.gestionpasos.model.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PlacedFigureRequest(
        @NotNull UUID idHermandad,
        @NotNull UUID idPlantillaPaso,
        @NotNull UUID idHuecoPaso,
        @NotNull UUID idFiguraPaso,
        Integer desplazamientoX,
        Integer desplazamientoY,
        BigDecimal escala,
        BigDecimal rotacion
) {
}
