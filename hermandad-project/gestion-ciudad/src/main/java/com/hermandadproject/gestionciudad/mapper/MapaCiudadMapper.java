package com.hermandadproject.gestionciudad.mapper;

import com.hermandadproject.gestionciudad.model.dto.MapaCiudadResponse;
import com.hermandadproject.gestionciudad.model.entity.MapaCiudadEntity;
import org.springframework.stereotype.Component;

@Component
public class MapaCiudadMapper {

    public MapaCiudadResponse toResponse(MapaCiudadEntity entity) {
        return new MapaCiudadResponse(
                entity.getId(),
                entity.getCiudad().getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getUrlRecurso(),
                entity.getAncho(),
                entity.getAlto(),
                entity.getActivo()
        );
    }
}
