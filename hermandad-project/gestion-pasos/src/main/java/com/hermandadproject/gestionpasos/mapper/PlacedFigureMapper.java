package com.hermandadproject.gestionpasos.mapper;

import com.hermandadproject.gestionpasos.model.dto.PlacedFigureResponse;
import com.hermandadproject.gestionpasos.model.entity.FiguraColocadaEntity;
import org.springframework.stereotype.Component;

@Component
public class PlacedFigureMapper {

    public PlacedFigureResponse toResponse(FiguraColocadaEntity entity) {
        return new PlacedFigureResponse(
                entity.getId(),
                entity.getIdHermandad(),
                entity.getPlantillaPaso().getId(),
                entity.getHuecoPaso().getId(),
                entity.getFiguraPaso().getId(),
                entity.getDesplazamientoX(),
                entity.getDesplazamientoY(),
                entity.getEscala(),
                entity.getRotacion()
        );
    }
}
