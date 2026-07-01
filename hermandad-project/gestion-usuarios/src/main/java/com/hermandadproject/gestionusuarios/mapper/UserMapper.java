package com.hermandadproject.gestionusuarios.mapper;

import com.hermandadproject.gestionusuarios.model.dto.UserResponse;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(UsuarioEntity entity) {
        return new UserResponse(
                entity.getId(),
                entity.getNombreUsuario(),
                entity.getCorreoElectronico(),
                entity.getRol(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion(),
                entity.getVigenteDesde(),
                entity.getVigenteHasta()
        );
    }
}
