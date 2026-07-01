package com.hermandadproject.gestionpasos.service;

import com.hermandadproject.gestionpasos.model.dto.PasoFigureResponse;
import com.hermandadproject.gestionpasos.model.enums.FigureTypeEnum;

import java.util.List;
import java.util.UUID;

public interface PasoFigureService {
    List<PasoFigureResponse> findAllActive();

    List<PasoFigureResponse> findByType(FigureTypeEnum tipo);

    PasoFigureResponse findById(UUID id);

    PasoFigureResponse findByCodigo(String codigo);
}
