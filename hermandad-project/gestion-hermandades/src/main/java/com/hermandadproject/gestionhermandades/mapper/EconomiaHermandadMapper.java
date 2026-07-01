package com.hermandadproject.gestionhermandades.mapper;

import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadResumenDto;
import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.model.entity.EconomiaHermandadEntity;
import org.springframework.stereotype.Component;

@Component
public class EconomiaHermandadMapper {

    public EconomiaHermandadDto toDto(EconomiaHermandadEntity entity) {
        return new EconomiaHermandadDto(
                entity.getUuid(),
                entity.getHermandad().getId(),
                entity.getSaldoActual(),
                entity.getIngresosMensuales(),
                entity.getGastosMensuales(),
                entity.getDeudaActual(),
                entity.getPatrimonioEstimado(),
                entity.getNivelEstabilidadEconomica(),
                entity.getFechaUltimaActualizacion()
        );
    }

    public EconomiaHermandadResumenDto toResumenDto(EconomiaHermandadEntity entity) {
        if (entity == null) {
            return null;
        }
        return new EconomiaHermandadResumenDto(
                entity.getSaldoActual(),
                entity.getDeudaActual(),
                entity.getPatrimonioEstimado(),
                entity.getNivelEstabilidadEconomica()
        );
    }

    public void updateEntity(EconomiaHermandadEntity entity, EconomiaHermandadUpdateDto dto) {
        entity.setIngresosMensuales(dto.ingresosMensuales());
        entity.setGastosMensuales(dto.gastosMensuales());
        entity.setDeudaActual(dto.deudaActual());
        entity.setPatrimonioEstimado(dto.patrimonioEstimado());
        entity.setNivelEstabilidadEconomica(dto.nivelEstabilidadEconomica());
    }
}

