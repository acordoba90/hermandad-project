package com.hermandadproject.gestionpasos.service;

import com.hermandadproject.gestionpasos.model.dto.PlacedFigureRequest;
import com.hermandadproject.gestionpasos.model.dto.PlacedFigureResponse;

import java.util.List;
import java.util.UUID;

public interface PasoCompositionService {
    PlacedFigureResponse placeFigure(PlacedFigureRequest request);

    List<PlacedFigureResponse> findComposition(UUID idHermandad, UUID idPlantillaPaso);

    void removePlacedFigure(UUID placedFigureId);
}
