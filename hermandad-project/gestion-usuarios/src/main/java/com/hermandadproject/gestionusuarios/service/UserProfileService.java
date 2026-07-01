package com.hermandadproject.gestionusuarios.service;

import com.hermandadproject.gestionusuarios.model.dto.UserProfileCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileUpdateRequest;

import java.util.UUID;

public interface UserProfileService {
    UserProfileResponse create(UserProfileCreateRequest request);

    UserProfileResponse findById(UUID id);

    UserProfileResponse findByUsuarioId(UUID idUsuario);

    UserProfileResponse update(UUID id, UserProfileUpdateRequest request);

    void delete(UUID id);
}
