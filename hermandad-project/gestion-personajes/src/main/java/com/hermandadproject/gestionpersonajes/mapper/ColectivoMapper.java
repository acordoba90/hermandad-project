package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.ColectivoCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoResponse;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import org.springframework.stereotype.Component;

@Component
public class ColectivoMapper {

    public ColectivoEntity toEntity(ColectivoCreateRequest request) {
        ColectivoEntity entity = new ColectivoEntity();
        entity.setCodigo(request.codigo());
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setActivo(true);
        return entity;
    }

    public ColectivoResponse toResponse(ColectivoEntity entity) {
        return new ColectivoResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getActivo(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    public void updateEntity(ColectivoEntity entity, ColectivoUpdateRequest request) {
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setActivo(request.activo());
    }
}
