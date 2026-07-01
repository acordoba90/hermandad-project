package com.hermandadproject.gestionusuarios.service;

import com.hermandadproject.gestionusuarios.model.dto.UserCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserValidationRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse getById(UUID id);

    UserResponse create(UserCreateRequest request);

    UserResponse validateCredentials(UserValidationRequest request);
}
