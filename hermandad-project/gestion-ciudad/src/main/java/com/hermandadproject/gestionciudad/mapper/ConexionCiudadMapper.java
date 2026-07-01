package com.hermandadproject.gestionciudad.mapper;

import com.hermandadproject.gestionciudad.model.dto.ConexionCiudadResponse;
import com.hermandadproject.gestionciudad.model.entity.ConexionCiudadEntity;
import org.springframework.stereotype.Component;

@Component
public class ConexionCiudadMapper {

    public ConexionCiudadResponse toResponse(ConexionCiudadEntity entity) {
        return new ConexionCiudadResponse(
                entity.getId(),
                entity.getMapaCiudad().getId(),
                entity.getNodoOrigen().getId(),
                entity.getNodoDestino().getId(),
                entity.getDistanciaMetros(),
                entity.getMinutosEstimados(),
                entity.getDificultad(),
                entity.getActiva()
        );
    }
}
