package com.hermandadproject.gestionhermandades.mapper;

import com.hermandadproject.gestionhermandades.model.dto.MovimientoEconomicoHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.MovimientoEconomicoHermandadDto;
import com.hermandadproject.gestionhermandades.model.entity.MovimientoEconomicoHermandadEntity;
import org.springframework.stereotype.Component;

@Component
public class MovimientoEconomicoHermandadMapper {

    public MovimientoEconomicoHermandadDto toDto(MovimientoEconomicoHermandadEntity entity) {
        return new MovimientoEconomicoHermandadDto(
                entity.getUuid(),
                entity.getHermandad().getId(),
                entity.getTipoMovimiento(),
                entity.getCategoria(),
                entity.getConcepto(),
                entity.getDescripcion(),
                entity.getImporte(),
                entity.getFechaMovimiento(),
                entity.getFechaRegistro()
        );
    }

    public MovimientoEconomicoHermandadEntity toEntity(MovimientoEconomicoHermandadCreateDto dto) {
        MovimientoEconomicoHermandadEntity entity = new MovimientoEconomicoHermandadEntity();
        entity.setTipoMovimiento(dto.tipoMovimiento());
        entity.setCategoria(dto.categoria());
        entity.setConcepto(dto.concepto());
        entity.setDescripcion(dto.descripcion());
        entity.setImporte(dto.importe());
        entity.setFechaMovimiento(dto.fechaMovimiento());
        return entity;
    }
}

