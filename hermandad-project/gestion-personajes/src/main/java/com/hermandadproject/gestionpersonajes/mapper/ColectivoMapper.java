package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.ColectivoCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoResponse;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import org.springframework.stereotype.Component;

/**
 * Convierte entre la entidad de colectivo y sus DTO de entrada y salida.
 */
@Component
public class ColectivoMapper {

    /**
     * Crea una entidad nueva a partir de la peticion de alta.
     *
     * @param request datos recibidos desde la API
     * @return entidad preparada para persistirse
     */
    public ColectivoEntity toEntity(ColectivoCreateRequest request) {
        ColectivoEntity entity = new ColectivoEntity();
        entity.setCodigo(request.codigo());
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setActivo(true);
        return entity;
    }

    /**
     * Convierte una entidad persistida en la respuesta expuesta por la API.
     *
     * @param entity entidad de colectivo
     * @return DTO de respuesta
     */
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

    /**
     * Aplica los campos modificables sin alterar codigo ni fechas de auditoria.
     *
     * @param entity entidad existente
     * @param request datos de actualizacion
     */
    public void updateEntity(ColectivoEntity entity, ColectivoUpdateRequest request) {
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setActivo(request.activo());
    }
}
