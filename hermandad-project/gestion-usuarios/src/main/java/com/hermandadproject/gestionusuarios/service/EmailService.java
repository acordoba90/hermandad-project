package com.hermandadproject.gestionusuarios.service;

import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;

import java.util.Map;

/**
 * Servicio de envio de correos transaccionales del modulo de usuarios.
 */
public interface EmailService {

    void enviarCorreoDesdeMarkdown(String destinatario, String asunto, String plantilla, Map<String, String> variables);

    void enviarCorreoBienvenida(UsuarioEntity usuario);
}
