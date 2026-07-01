package com.hermandadproject.gestionciudad.mapper;

import com.hermandadproject.gestionciudad.model.dto.NodoCiudadResponse;
import com.hermandadproject.gestionciudad.model.entity.NodoCiudadEntity;
import org.springframework.stereotype.Component;

@Component
public class NodoCiudadMapper {

    public NodoCiudadResponse toResponse(NodoCiudadEntity entity) {
        return new NodoCiudadResponse(
                entity.getId(),
                entity.getMapaCiudad().getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getTipo(),
                entity.getPosicionX(),
                entity.getPosicionY(),
                entity.getDistrito(),
                entity.getAnchuraVia(),
                entity.getNivelPublico(),
                entity.getDificultad(),
                entity.getActivo()
        );
    }
}
