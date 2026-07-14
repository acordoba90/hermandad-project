package com.hermandadproject.gestionusuarios.controller;

import com.hermandadproject.gestionusuarios.model.dto.AccountActivationRequest;
import com.hermandadproject.gestionusuarios.service.EmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/activacion-token-expirado")
    public ResponseEntity<Void> enviarCorreoExpiracionTokenActivacion(
            @Valid @RequestBody AccountActivationRequest request
    ) {
        LOGGER.info("Peticion REST publica para enviar correo por expiracion de token de activacion. actor=ANONYMOUS");
        emailService.enviarCorreoExpiracionTokenActivacion(request.token());
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        LOGGER.info(
                "Peticion de envio de correo por expiracion de token completada. status={}",
                response.getStatusCode().value()
        );
        return response;
    }
}
