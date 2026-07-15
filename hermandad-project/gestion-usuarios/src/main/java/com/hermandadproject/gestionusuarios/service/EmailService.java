package com.hermandadproject.gestionusuarios.service;

import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;

import java.util.Map;

/**
 * Servicio de envio de correos transaccionales del modulo de usuarios.
 */
public interface EmailService {

    void enviarCorreoDesdeMarkdown(String destinatario, String asunto, String plantilla, Map<String, String> variables);

    void enviarCorreoBienvenida(UsuarioEntity usuario);

    void enviarCorreoExpiracionTokenActivacion(String token);

    /**
     * Envia un correo con el enlace de restauracion de contrasena.
     *
     * @param usuario usuario destinatario.
     * @param enlaceRestauracion enlace completo hacia el frontend.
     * @param tiempoExpiracion descripcion publica de la vigencia del enlace.
     */
    void enviarCorreoRestauracionContrasena(
            UsuarioEntity usuario,
            String enlaceRestauracion,
            String tiempoExpiracion
    );
}
