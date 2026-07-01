package com.hermandadproject.gestionhermandades.mapper;

import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadResumenDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.model.entity.CarismaHermandadEntity;
import org.springframework.stereotype.Component;

@Component
public class CarismaHermandadMapper {

    public CarismaHermandadDto toDto(CarismaHermandadEntity entity) {
        return new CarismaHermandadDto(
                entity.getUuid(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getActivo(),
                entity.getOrden(),
                entity.getPrestigioBase(),
                entity.getPopularidadBase(),
                entity.getSolemnidadBase(),
                entity.getDevocionBase(),
                entity.getImpactoEconomicoBase()
        );
    }

    public CarismaHermandadResumenDto toResumenDto(CarismaHermandadEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CarismaHermandadResumenDto(entity.getUuid(), entity.getCodigo(), entity.getNombre());
    }

    public CarismaHermandadEntity toEntity(CarismaHermandadCreateDto dto) {
        CarismaHermandadEntity entity = new CarismaHermandadEntity();
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setActivo(dto.activo());
        entity.setOrden(dto.orden());
        entity.setPrestigioBase(dto.prestigioBase());
        entity.setPopularidadBase(dto.popularidadBase());
        entity.setSolemnidadBase(dto.solemnidadBase());
        entity.setDevocionBase(dto.devocionBase());
        entity.setImpactoEconomicoBase(dto.impactoEconomicoBase());
        return entity;
    }

    public void updateEntity(CarismaHermandadEntity entity, CarismaHermandadUpdateDto dto) {
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setActivo(dto.activo());
        entity.setOrden(dto.orden());
        entity.setPrestigioBase(dto.prestigioBase());
        entity.setPopularidadBase(dto.popularidadBase());
        entity.setSolemnidadBase(dto.solemnidadBase());
        entity.setDevocionBase(dto.devocionBase());
        entity.setImpactoEconomicoBase(dto.impactoEconomicoBase());
    }
}

