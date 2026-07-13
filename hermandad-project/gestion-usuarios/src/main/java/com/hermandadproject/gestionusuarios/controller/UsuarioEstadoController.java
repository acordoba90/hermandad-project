package com.hermandadproject.gestionusuarios.controller;

import com.hermandadproject.gestionusuarios.model.dto.AccountActivationRequest;
import com.hermandadproject.gestionusuarios.service.UsuarioEstadoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios-estado")
public class UsuarioEstadoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioEstadoController.class);

    private final UsuarioEstadoService usuarioEstadoService;

    public UsuarioEstadoController(UsuarioEstadoService usuarioEstadoService) {
        this.usuarioEstadoService = usuarioEstadoService;
    }

    @PostMapping("/activar-cuenta")
    public ResponseEntity<Void> activarCuenta(@Valid @RequestBody AccountActivationRequest request) {
        LOGGER.info("Peticion REST publica para activar cuenta. actor=ANONYMOUS");
        usuarioEstadoService.activarCuenta(request.token());
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        LOGGER.info("Peticion de activacion de cuenta completada. status={}", response.getStatusCode().value());
        return response;
    }
}
