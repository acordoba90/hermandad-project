package com.hermandadproject.gestionusuarios.controller;

import com.hermandadproject.gestionusuarios.model.dto.UserProfileCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileUpdateRequest;
import com.hermandadproject.gestionusuarios.service.UserProfileService;
import jakarta.validation.Valid;
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

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfileResponse> create(
            @Valid @RequestBody UserProfileCreateRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        UserProfileResponse created = userProfileService.create(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/usuario-profiles/{id}").buildAndExpand(created.id()).toUri())
                .body(created);
    }

    @GetMapping("/{id}")
    public UserProfileResponse findById(@PathVariable UUID id) {
        return userProfileService.findById(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    public UserProfileResponse findByUsuarioId(@PathVariable UUID idUsuario) {
        return userProfileService.findByUsuarioId(idUsuario);
    }

    @PutMapping("/{id}")
    public UserProfileResponse update(@PathVariable UUID id, @Valid @RequestBody UserProfileUpdateRequest request) {
        return userProfileService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
