package com.hermandadproject.gestionusuarios.controller;

import jakarta.validation.Valid;
import com.hermandadproject.gestionusuarios.model.dto.UserCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserValidationRequest;
import com.hermandadproject.gestionusuarios.service.UserService;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request, UriComponentsBuilder uriBuilder) {
        UserResponse created = userService.create(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/users/{id}").buildAndExpand(created.id()).toUri())
                .body(created);
    }

    @PostMapping("/validate")
    public UserResponse validateCredentials(@Valid @RequestBody UserValidationRequest request) {
        return userService.validateCredentials(request);
    }

}
