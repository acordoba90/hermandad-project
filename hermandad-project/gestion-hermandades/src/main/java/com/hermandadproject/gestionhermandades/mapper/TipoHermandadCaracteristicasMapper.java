package com.hermandadproject.gestionhermandades.mapper;

import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadCaracteristicasResponse;
import com.hermandadproject.gestionhermandades.model.entity.TipoHermandadCaracteristicasEntity;
import org.springframework.stereotype.Component;

@Component
public class TipoHermandadCaracteristicasMapper {

    public TipoHermandadCaracteristicasResponse toResponse(TipoHermandadCaracteristicasEntity entity) {
        if (entity == null) {
            return null;
        }
        return new TipoHermandadCaracteristicasResponse(
                entity.getUuid(),
                entity.getTipoHermandad().getUuid(),
                entity.getTipoHermandad().getCodigo(),
                entity.getResumenJugable(),
                entity.getCosteMantenimientoBase(),
                entity.getIngresosBase(),
                entity.getDificultadBase(),
                entity.getDevocionBase(),
                entity.getInfluenciaEclesiasticaBase(),
                entity.getInfluenciaSocialBase(),
                entity.getCapacidadCrecimiento(),
                entity.getPermiteCarreraOficial(),
                entity.getPermitePatrimonioAvanzado(),
                entity.getPermiteBandaMusica(),
                entity.getPermiteCuerpoNazarenos(),
                entity.getPermiteCuadrillaCostaleros(),
                entity.getTipoPrevioRequerido(),
                entity.getRequisitosEvolucion(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
