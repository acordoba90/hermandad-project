package com.hermandadproject.gestionciudad.mapper;

import com.hermandadproject.gestionciudad.model.dto.CiudadResponse;
import com.hermandadproject.gestionciudad.model.entity.CiudadEntity;
import org.springframework.stereotype.Component;

@Component
public class CiudadMapper {

    public CiudadResponse toResponse(CiudadEntity entity) {
        return new CiudadResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getActiva(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
