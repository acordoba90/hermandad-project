package com.hermandadproject.gestionpasos.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PlacedFigureResponse(
        UUID id,
        UUID idHermandad,
        UUID idPlantillaPaso,
        UUID idHuecoPaso,
        UUID idFiguraPaso,
        Integer desplazamientoX,
        Integer desplazamientoY,
        BigDecimal escala,
        BigDecimal rotacion
) {
}
