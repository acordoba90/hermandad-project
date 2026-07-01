package com.hermandadproject.gestionpasos.mapper;

import com.hermandadproject.gestionpasos.model.dto.PasoTemplateResponse;
import com.hermandadproject.gestionpasos.model.entity.PlantillaPasoEntity;
import org.springframework.stereotype.Component;

@Component
public class PasoTemplateMapper {

    public PasoTemplateResponse toResponse(PlantillaPasoEntity entity) {
        return new PasoTemplateResponse(
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
