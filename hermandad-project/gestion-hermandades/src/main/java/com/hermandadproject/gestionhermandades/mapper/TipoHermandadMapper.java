package com.hermandadproject.gestionhermandades.mapper;

import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadResumenDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.model.entity.TipoHermandadEntity;
import org.springframework.stereotype.Component;

@Component
public class TipoHermandadMapper {

    private final TipoHermandadCaracteristicasMapper caracteristicasMapper;

    public TipoHermandadMapper(TipoHermandadCaracteristicasMapper caracteristicasMapper) {
        this.caracteristicasMapper = caracteristicasMapper;
    }

    public TipoHermandadDto toDto(TipoHermandadEntity entity) {
        return new TipoHermandadDto(
                entity.getUuid(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getNivel(),
                entity.getActivo(),
                entity.getPuedeEstacionPenitencia(),
                entity.getPuedeCultosExternos(),
                entity.getPuedeTenerSedeCanonica(),
                entity.getPuedeTenerPaso(),
                entity.getPrestigioBase(),
                entity.getOrden(),
                caracteristicasMapper.toResponse(entity.getCaracteristicas())
        );
    }

    public TipoHermandadResumenDto toResumenDto(TipoHermandadEntity entity) {
        if (entity == null) {
            return null;
        }
        return new TipoHermandadResumenDto(entity.getUuid(), entity.getCodigo(), entity.getNombre(), entity.getNivel());
    }

    public TipoHermandadEntity toEntity(TipoHermandadCreateDto dto) {
        TipoHermandadEntity entity = new TipoHermandadEntity();
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setNivel(dto.nivel());
        entity.setActivo(dto.activo());
        entity.setPuedeEstacionPenitencia(dto.puedeEstacionPenitencia());
        entity.setPuedeCultosExternos(dto.puedeCultosExternos());
        entity.setPuedeTenerSedeCanonica(dto.puedeTenerSedeCanonica());
        entity.setPuedeTenerPaso(dto.puedeTenerPaso());
        entity.setPrestigioBase(dto.prestigioBase());
        entity.setOrden(dto.orden());
        return entity;
    }

    public void updateEntity(TipoHermandadEntity entity, TipoHermandadUpdateDto dto) {
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setNivel(dto.nivel());
        entity.setActivo(dto.activo());
        entity.setPuedeEstacionPenitencia(dto.puedeEstacionPenitencia());
        entity.setPuedeCultosExternos(dto.puedeCultosExternos());
        entity.setPuedeTenerSedeCanonica(dto.puedeTenerSedeCanonica());
        entity.setPuedeTenerPaso(dto.puedeTenerPaso());
        entity.setPrestigioBase(dto.prestigioBase());
        entity.setOrden(dto.orden());
    }
}
