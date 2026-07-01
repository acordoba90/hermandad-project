package com.hermandadproject.gestionpasos.mapper;

import com.hermandadproject.gestionpasos.model.dto.PasoFigureResponse;
import com.hermandadproject.gestionpasos.model.entity.FiguraPasoEntity;
import org.springframework.stereotype.Component;

@Component
public class PasoFigureMapper {

    public PasoFigureResponse toResponse(FiguraPasoEntity entity) {
        return new PasoFigureResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getTipo(),
                entity.getUrlRecurso(),
                entity.getPrecio(),
                entity.getPrestigioRequerido(),
                entity.getActivo()
        );
    }
}
