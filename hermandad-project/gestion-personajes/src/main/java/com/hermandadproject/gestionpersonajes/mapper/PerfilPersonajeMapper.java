package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.PerfilPersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ArquetipoPerfilEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PerfilPersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import org.springframework.stereotype.Component;

/**
 * Convierte perfiles de personaje y crea perfiles iniciales desde arquetipos.
 */
@Component
public class PerfilPersonajeMapper {

    /**
     * Crea un perfil evolutivo copiando los atributos base del arquetipo recibido.
     *
     * @param personaje personaje propietario del perfil
     * @param arquetipo arquetipo de origen
     * @return perfil listo para persistirse
     */
    public PerfilPersonajeEntity crearDesdeArquetipo(PersonajeEntity personaje, ArquetipoPerfilEntity arquetipo) {
        PerfilPersonajeEntity perfil = new PerfilPersonajeEntity();
        perfil.setPersonaje(personaje);
        perfil.setArquetipoOrigen(arquetipo);
        perfil.setNivel(1);
        perfil.setExperiencia(0L);
        perfil.setPuntosDesarrollo(0);
        perfil.setLiderazgo(arquetipo.getLiderazgoBase());
        perfil.setCarisma(arquetipo.getCarismaBase());
        perfil.setDiplomacia(arquetipo.getDiplomaciaBase());
        perfil.setOrganizacion(arquetipo.getOrganizacionBase());
        perfil.setComunicacion(arquetipo.getComunicacionBase());
        perfil.setInfluencia(arquetipo.getInfluenciaBase());
        perfil.setConocimientoCofrade(arquetipo.getConocimientoCofradeBase());
        perfil.setProtocolo(arquetipo.getProtocoloBase());
        perfil.setDevocion(arquetipo.getDevocionBase());
        perfil.setDisciplina(arquetipo.getDisciplinaBase());
        perfil.setEmpatia(arquetipo.getEmpatiaBase());
        perfil.setLealtad(arquetipo.getLealtadBase());
        perfil.setIntegridad(arquetipo.getIntegridadBase());
        perfil.setAmbicion(arquetipo.getAmbicionBase());
        perfil.setConflictividad(arquetipo.getConflictividadBase());
        perfil.setPopularidad(arquetipo.getPopularidadBase());
        perfil.setReputacion(arquetipo.getReputacionBase());
        perfil.setActivo(true);
        return perfil;
    }

    /**
     * Convierte un perfil persistido en respuesta sin exponer entidades relacionadas completas.
     *
     * @param entity perfil persistido
     * @return DTO de respuesta
     */
    public PerfilPersonajeResponse toResponse(PerfilPersonajeEntity entity) {
        ArquetipoPerfilEntity arquetipo = entity.getArquetipoOrigen();
        return new PerfilPersonajeResponse(
                entity.getId(),
                entity.getPersonaje().getId(),
                arquetipo == null ? null : arquetipo.getId(),
                arquetipo == null ? null : arquetipo.getCodigo(),
                arquetipo == null ? null : arquetipo.getNombre(),
                arquetipo == null ? null : arquetipo.getDescripcion(),
                entity.getNivel(),
                entity.getExperiencia(),
                entity.getPuntosDesarrollo(),
                entity.getLiderazgo(),
                entity.getCarisma(),
                entity.getDiplomacia(),
                entity.getOrganizacion(),
                entity.getComunicacion(),
                entity.getInfluencia(),
                entity.getConocimientoCofrade(),
                entity.getProtocolo(),
                entity.getDevocion(),
                entity.getDisciplina(),
                entity.getEmpatia(),
                entity.getLealtad(),
                entity.getIntegridad(),
                entity.getAmbicion(),
                entity.getConflictividad(),
                entity.getPopularidad(),
                entity.getReputacion(),
                entity.getActivo(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
