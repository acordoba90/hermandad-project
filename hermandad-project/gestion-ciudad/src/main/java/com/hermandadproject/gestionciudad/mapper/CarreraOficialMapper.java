package com.hermandadproject.gestionciudad.mapper;

import com.hermandadproject.gestionciudad.model.dto.CarreraOficialResponse;
import com.hermandadproject.gestionciudad.model.entity.CarreraOficialEntity;
import org.springframework.stereotype.Component;

@Component
public class CarreraOficialMapper {

    public CarreraOficialResponse toResponse(CarreraOficialEntity entity) {
        return new CarreraOficialResponse(
                entity.getId(),
                entity.getCiudad().getId(),
                entity.getMapaCiudad().getId(),
                entity.getNodoEntrada().getId(),
                entity.getNodoSalida().getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getMinutosEstimados(),
                entity.getActiva()
        );
    }
}
