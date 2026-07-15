package com.hermandadproject.gestionusuarios.service;

import com.hermandadproject.gestionusuarios.model.dto.UserCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserValidationRequest;
import com.hermandadproject.gestionusuarios.model.dto.ConfirmacionRestauracionContrasenaRequest;
import com.hermandadproject.gestionusuarios.model.dto.SolicitudRestauracionContrasenaRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getAll();

    UserResponse getById(UUID id);

    UserResponse create(UserCreateRequest request);

    UserResponse validateCredentials(UserValidationRequest request);

    /**
     * Solicita el envio de un correo de restauracion de contrasena.
     *
     * @param request correo de la cuenta que solicita la restauracion.
     */
    void solicitarRestauracionContrasena(SolicitudRestauracionContrasenaRequest request);

    /**
     * Confirma la restauracion de contrasena usando un token vigente.
     *
     * @param request token y nueva contrasena.
     */
    void confirmarRestauracionContrasena(ConfirmacionRestauracionContrasenaRequest request);
}
