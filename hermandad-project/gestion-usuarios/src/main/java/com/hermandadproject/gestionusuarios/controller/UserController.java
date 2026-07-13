package com.hermandadproject.gestionusuarios.controller;

import com.hermandadproject.gestionusuarios.logging.ActorContext;
import com.hermandadproject.gestionusuarios.logging.CurrentUserContext;
import com.hermandadproject.gestionusuarios.logging.SensitiveDataMasker;
import com.hermandadproject.gestionusuarios.model.dto.UserCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserValidationRequest;
import com.hermandadproject.gestionusuarios.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final CurrentUserContext currentUserContext;

    public UserController(UserService userService, CurrentUserContext currentUserContext) {
        this.userService = userService;
        this.currentUserContext = currentUserContext;
    }

    @GetMapping
    public List<UserResponse> getAll() {
        ActorContext actor = currentUserContext.getCurrentActor();
        LOGGER.info(
                "Peticion REST para listar usuarios. actorUsuarioId={}, actorNombreUsuario={}",
                actor.actorUsuarioId(),
                actor.actorNombreUsuario()
        );
        List<UserResponse> users = userService.getAll();
        LOGGER.info("Listado de usuarios completado. status={}, total={}", HttpStatus.OK.value(), users.size());
        return users;
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        ActorContext actor = currentUserContext.getCurrentActor();
        LOGGER.info(
                "Peticion REST para consultar usuario. actorUsuarioId={}, actorNombreUsuario={}, usuarioObjetivoId={}",
                actor.actorUsuarioId(),
                actor.actorNombreUsuario(),
                id
        );
        UserResponse response = userService.getById(id);
        LOGGER.info("Consulta de usuario completada. status={}, usuarioObjetivoId={}", HttpStatus.OK.value(), id);
        return response;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request, UriComponentsBuilder uriBuilder) {
        LOGGER.info(
                "Peticion REST publica para registrar usuario. actor=ANONYMOUS, nombreUsuario={}, correo={}",
                request.nombreUsuario(),
                SensitiveDataMasker.maskEmail(request.correoElectronico())
        );
        UserResponse created = userService.create(request);
        ResponseEntity<UserResponse> response = ResponseEntity
                .created(uriBuilder.path("/api/users/{id}").buildAndExpand(created.id()).toUri())
                .body(created);
        LOGGER.info(
                "Peticion de registro completada. status={}, usuarioObjetivoId={}, nombreUsuario={}",
                response.getStatusCode().value(),
                created.id(),
                created.nombreUsuario()
        );
        return response;
    }

    @PostMapping("/validate")
    public UserResponse validateCredentials(@Valid @RequestBody UserValidationRequest request) {
        LOGGER.info(
                "Peticion REST publica para validar credenciales. actor=ANONYMOUS, correo={}",
                SensitiveDataMasker.maskEmail(request.correoElectronico())
        );
        UserResponse response = userService.validateCredentials(request);
        LOGGER.info(
                "Validacion de credenciales completada. status={}, usuarioObjetivoId={}, nombreUsuario={}",
                HttpStatus.OK.value(),
                response.id(),
                response.nombreUsuario()
        );
        return response;
    }

}
