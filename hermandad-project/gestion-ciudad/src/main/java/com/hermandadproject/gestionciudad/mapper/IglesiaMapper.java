package com.hermandadproject.gestionciudad.mapper;

import com.hermandadproject.gestionciudad.model.dto.IglesiaResponse;
import com.hermandadproject.gestionciudad.model.entity.IglesiaEntity;
import org.springframework.stereotype.Component;

@Component
public class IglesiaMapper {

    public IglesiaResponse toResponse(IglesiaEntity entity) {
        return new IglesiaResponse(
                entity.getId(),
                entity.getCiudad().getId(),
                entity.getNodoCiudad().getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getCapacidad(),
                entity.getPrestigio(),
                entity.getDisponibleComoSede(),
                entity.getConstruible(),
                entity.getCosteConstruccion(),
                entity.getMesesConstruccion(),
                entity.getActiva()
        );
    }
}
