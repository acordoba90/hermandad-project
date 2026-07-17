package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.RolPersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import org.springframework.stereotype.Component;

/**
 * Convierte roles de personaje persistidos en los DTO expuestos por la API.
 */
@Component
public class RolPersonajeMapper {

    /**
     * Convierte una entidad de rol en su representacion de salida sin exponer relaciones JPA completas.
     *
     * @param entity rol de personaje persistido
     * @return DTO de respuesta del rol
     */
    public RolPersonajeResponse toResponse(RolPersonajeEntity entity) {
        ColectivoEntity colectivo = entity.getColectivo();
        return new RolPersonajeResponse(
                entity.getId(),
                colectivo.getId(),
                colectivo.getCodigo(),
                colectivo.getNombre(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getActivo(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
