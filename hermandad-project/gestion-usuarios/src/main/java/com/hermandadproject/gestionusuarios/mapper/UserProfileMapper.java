package com.hermandadproject.gestionusuarios.mapper;

import com.hermandadproject.gestionusuarios.model.dto.UserProfileCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileUpdateRequest;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.PerfilUsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public PerfilUsuarioEntity toEntity(UserProfileCreateRequest request, UsuarioEntity usuario) {
        PerfilUsuarioEntity entity = new PerfilUsuarioEntity();
        entity.setUsuario(usuario);
        entity.setAlias(request.alias());
        entity.setUrlAvatar(request.urlAvatar());
        return entity;
    }

    public UserProfileResponse toResponse(PerfilUsuarioEntity entity) {
        return new UserProfileResponse(
                entity.getId(),
                entity.getUsuario().getId(),
                entity.getAlias(),
                entity.getUrlAvatar(),
                entity.getNivel(),
                entity.getExperiencia(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    public void updateEntity(PerfilUsuarioEntity entity, UserProfileUpdateRequest request) {
        entity.setAlias(request.alias());
        entity.setUrlAvatar(request.urlAvatar());
    }
}
