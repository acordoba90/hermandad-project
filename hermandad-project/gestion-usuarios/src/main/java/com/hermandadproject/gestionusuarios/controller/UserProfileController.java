package com.hermandadproject.gestionusuarios.controller;

import com.hermandadproject.gestionusuarios.logging.ActorContext;
import com.hermandadproject.gestionusuarios.logging.CurrentUserContext;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileUpdateRequest;
import com.hermandadproject.gestionusuarios.service.UserProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuario-profiles")
public class UserProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    private final UserProfileService userProfileService;
    private final CurrentUserContext currentUserContext;

    public UserProfileController(UserProfileService userProfileService, CurrentUserContext currentUserContext) {
        this.userProfileService = userProfileService;
        this.currentUserContext = currentUserContext;
    }

    @PostMapping
    public ResponseEntity<UserProfileResponse> create(
            @Valid @RequestBody UserProfileCreateRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        ActorContext actor = currentUserContext.getCurrentActor();
        LOGGER.info(
                "Peticion REST para crear perfil de usuario. actorUsuarioId={}, actorNombreUsuario={}, usuarioObjetivoId={}, alias={}",
                actor.actorUsuarioId(),
                actor.actorNombreUsuario(),
                request.idUsuario(),
                request.alias()
        );
        UserProfileResponse created = userProfileService.create(request);
        ResponseEntity<UserProfileResponse> response = ResponseEntity
                .created(uriBuilder.path("/api/usuario-profiles/{id}").buildAndExpand(created.id()).toUri())
                .body(created);
        LOGGER.info(
                "Creacion de perfil completada. status={}, perfilId={}, usuarioObjetivoId={}",
                response.getStatusCode().value(),
                created.id(),
                created.idUsuario()
        );
        return response;
    }

    @GetMapping("/{id}")
    public UserProfileResponse findById(@PathVariable UUID id) {
        ActorContext actor = currentUserContext.getCurrentActor();
        LOGGER.info(
                "Peticion REST para consultar perfil. actorUsuarioId={}, actorNombreUsuario={}, perfilId={}",
                actor.actorUsuarioId(),
                actor.actorNombreUsuario(),
                id
        );
        UserProfileResponse response = userProfileService.findById(id);
        LOGGER.info("Consulta de perfil completada. status={}, perfilId={}", HttpStatus.OK.value(), id);
        return response;
    }

    @GetMapping("/usuario/{idUsuario}")
    public UserProfileResponse findByUsuarioId(@PathVariable UUID idUsuario) {
        ActorContext actor = currentUserContext.getCurrentActor();
        LOGGER.info(
                "Peticion REST para consultar perfil por usuario. actorUsuarioId={}, actorNombreUsuario={}, usuarioObjetivoId={}",
                actor.actorUsuarioId(),
                actor.actorNombreUsuario(),
                idUsuario
        );
        UserProfileResponse response = userProfileService.findByUsuarioId(idUsuario);
        LOGGER.info(
                "Consulta de perfil por usuario completada. status={}, usuarioObjetivoId={}, perfilId={}",
                HttpStatus.OK.value(),
                idUsuario,
                response.id()
        );
        return response;
    }

    @PutMapping("/{id}")
    public UserProfileResponse update(@PathVariable UUID id, @Valid @RequestBody UserProfileUpdateRequest request) {
        ActorContext actor = currentUserContext.getCurrentActor();
        LOGGER.info(
                "Peticion REST para actualizar perfil. actorUsuarioId={}, actorNombreUsuario={}, perfilId={}, alias={}",
                actor.actorUsuarioId(),
                actor.actorNombreUsuario(),
                id,
                request.alias()
        );
        UserProfileResponse response = userProfileService.update(id, request);
        LOGGER.info("Actualizacion de perfil completada. status={}, perfilId={}", HttpStatus.OK.value(), id);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ActorContext actor = currentUserContext.getCurrentActor();
        LOGGER.info(
                "Peticion REST para eliminar perfil. actorUsuarioId={}, actorNombreUsuario={}, perfilId={}",
                actor.actorUsuarioId(),
                actor.actorNombreUsuario(),
                id
        );
        userProfileService.delete(id);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        LOGGER.info("Eliminacion de perfil completada. status={}, perfilId={}", response.getStatusCode().value(), id);
        return response;
    }
}
