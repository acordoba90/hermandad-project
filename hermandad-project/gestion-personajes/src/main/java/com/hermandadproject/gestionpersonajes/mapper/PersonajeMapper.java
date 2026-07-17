package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.PersonajeCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonajeMapper {

    private final PerfilPersonajeMapper perfilPersonajeMapper;

    public PersonajeMapper(PerfilPersonajeMapper perfilPersonajeMapper) {
        this.perfilPersonajeMapper = perfilPersonajeMapper;
    }

    /**
     * Crea una entidad de personaje a partir de datos ya validados y entidades de catalogo resueltas.
     *
     * @param request datos de creacion recibidos por API
     * @param colectivo colectivo validado para el personaje
     * @param rolPersonaje rol validado para el colectivo
     * @return entidad lista para persistirse
     */
    public PersonajeEntity toEntity(
            PersonajeCreateRequest request,
            ColectivoEntity colectivo,
            RolPersonajeEntity rolPersonaje
    ) {
        PersonajeEntity entity = new PersonajeEntity();
        entity.setCodigo(request.codigo());
        entity.setUsuarioId(request.usuarioId());
        entity.setAvatarId(request.avatarId());
        entity.setColectivo(colectivo);
        entity.setRolPersonaje(rolPersonaje);
        entity.setNombre(request.nombre());
        entity.setApellidos(request.apellidos());
        entity.setEdad(request.edad());
        entity.setGenero(request.genero());
        entity.setOrigen(request.origen());
        entity.setProfesion(request.profesion());
        entity.setDescripcion(request.descripcion());
        entity.setBiografia(request.biografia());
        entity.setMotivacion(request.motivacion());
        entity.setTipoPersonaje(request.tipoPersonaje());
        entity.setPersonalizado(request.personalizado());
        entity.setUrlAvatar(request.urlAvatar());
        entity.setActivo(true);
        return entity;
    }

    public PersonajeResponse toResponse(PersonajeEntity entity) {
        ColectivoEntity colectivo = entity.getColectivo();
        RolPersonajeEntity rolPersonaje = entity.getRolPersonaje();
        return new PersonajeResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getUsuarioId(),
                entity.getAvatarId(),
                colectivo.getId(),
                colectivo.getCodigo(),
                colectivo.getNombre(),
                rolPersonaje == null ? null : rolPersonaje.getId(),
                rolPersonaje == null ? null : rolPersonaje.getCodigo(),
                rolPersonaje == null ? null : rolPersonaje.getNombre(),
                entity.getNombre(),
                entity.getApellidos(),
                entity.getEdad(),
                entity.getGenero(),
                entity.getOrigen(),
                entity.getProfesion(),
                entity.getDescripcion(),
                entity.getBiografia(),
                entity.getMotivacion(),
                entity.getTipoPersonaje(),
                entity.getPersonalizado(),
                entity.getUrlAvatar(),
                entity.getActivo(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion(),
                entity.getPerfil() == null ? null : perfilPersonajeMapper.toResponse(entity.getPerfil())
        );
    }

    /**
     * Aplica los campos modificables de personaje manteniendo codigo y fechas de auditoria.
     *
     * @param entity entidad existente
     * @param request datos de actualizacion
     * @param colectivo colectivo validado
     * @param rolPersonaje rol validado para el colectivo
     */
    public void updateEntity(
            PersonajeEntity entity,
            PersonajeUpdateRequest request,
            ColectivoEntity colectivo,
            RolPersonajeEntity rolPersonaje
    ) {
        entity.setUsuarioId(request.usuarioId());
        entity.setAvatarId(request.avatarId());
        entity.setColectivo(colectivo);
        entity.setRolPersonaje(rolPersonaje);
        entity.setNombre(request.nombre());
        entity.setApellidos(request.apellidos());
        entity.setEdad(request.edad());
        entity.setGenero(request.genero());
        entity.setOrigen(request.origen());
        entity.setProfesion(request.profesion());
        entity.setDescripcion(request.descripcion());
        entity.setBiografia(request.biografia());
        entity.setMotivacion(request.motivacion());
        entity.setTipoPersonaje(request.tipoPersonaje());
        entity.setPersonalizado(request.personalizado());
        entity.setUrlAvatar(request.urlAvatar());
        entity.setActivo(request.activo());
    }
}
