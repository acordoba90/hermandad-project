package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.ArquetipoPerfilResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ArquetipoPerfilEntity;
import org.springframework.stereotype.Component;

/**
 * Convierte arquetipos de perfil a DTOs de API.
 */
@Component
public class ArquetipoPerfilMapper {

    /**
     * Convierte un arquetipo persistido en respuesta de consulta.
     *
     * @param entity arquetipo persistido
     * @return DTO de respuesta
     */
    public ArquetipoPerfilResponse toResponse(ArquetipoPerfilEntity entity) {
        return new ArquetipoPerfilResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getLiderazgoBase(),
                entity.getCarismaBase(),
                entity.getDiplomaciaBase(),
                entity.getOrganizacionBase(),
                entity.getComunicacionBase(),
                entity.getInfluenciaBase(),
                entity.getConocimientoCofradeBase(),
                entity.getProtocoloBase(),
                entity.getDevocionBase(),
                entity.getDisciplinaBase(),
                entity.getEmpatiaBase(),
                entity.getLealtadBase(),
                entity.getIntegridadBase(),
                entity.getAmbicionBase(),
                entity.getConflictividadBase(),
                entity.getPopularidadBase(),
                entity.getReputacionBase(),
                entity.getActivo(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
