package com.hermandadproject.gestionpersonajes.mapper;

import com.hermandadproject.gestionpersonajes.model.dto.PersonajeCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonajeMapper {

    public PersonajeEntity toEntity(PersonajeCreateRequest request, ColectivoEntity colectivo) {
        PersonajeEntity entity = new PersonajeEntity();
        entity.setCodigo(request.codigo());
        entity.setColectivo(colectivo);
        entity.setNombre(request.nombre());
        entity.setApellidos(request.apellidos());
        entity.setEdad(request.edad());
        entity.setGenero(request.genero());
        entity.setOrigen(request.origen());
        entity.setDescripcion(request.descripcion());
        entity.setUrlAvatar(request.urlAvatar());
        entity.setActivo(true);
        return entity;
    }

    public PersonajeResponse toResponse(PersonajeEntity entity) {
        ColectivoEntity colectivo = entity.getColectivo();
        return new PersonajeResponse(
                entity.getId(),
                entity.getCodigo(),
                colectivo.getId(),
                colectivo.getCodigo(),
                colectivo.getNombre(),
                entity.getNombre(),
                entity.getApellidos(),
                entity.getEdad(),
                entity.getGenero(),
                entity.getOrigen(),
                entity.getDescripcion(),
                entity.getUrlAvatar(),
                entity.getActivo(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    public void updateEntity(PersonajeEntity entity, PersonajeUpdateRequest request, ColectivoEntity colectivo) {
        entity.setColectivo(colectivo);
        entity.setNombre(request.nombre());
        entity.setApellidos(request.apellidos());
        entity.setEdad(request.edad());
        entity.setGenero(request.genero());
        entity.setOrigen(request.origen());
        entity.setDescripcion(request.descripcion());
        entity.setUrlAvatar(request.urlAvatar());
        entity.setActivo(request.activo());
    }
}
