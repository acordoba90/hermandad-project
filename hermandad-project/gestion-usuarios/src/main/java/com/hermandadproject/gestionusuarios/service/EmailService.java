package com.hermandadproject.gestionusuarios.service;

import com.hermandadproject.gestionusuarios.exception.EmailSendingException;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public interface EmailService {

    void enviarCorreoDesdeMarkdown(String destinatario, String asunto, String plantilla, Map<String, String> variables);
    void enviarCorreoBienvenida(UsuarioEntity usuario);
}