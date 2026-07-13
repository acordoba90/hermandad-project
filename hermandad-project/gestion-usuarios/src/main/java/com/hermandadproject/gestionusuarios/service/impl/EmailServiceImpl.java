package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.exception.EmailSendingException;
import com.hermandadproject.gestionusuarios.logging.SensitiveDataMasker;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private static final String EMAIL_TEMPLATES_PATH = "templates/emails/";
    private static final String WELCOME_EMAIL_TEMPLATE = "bienvenida.md";
    private static final String WELCOME_EMAIL_SUBJECT = "Bienvenido a Hermandad Project";
    private static final String TOKEN_EXPIRED_TEMPLATE = "tokenActivacionExpirado.md";
    private static final String TOKEN_EXPIRED_SUBJECT = "Nuevo enlace para activar tu cuenta de Hermandad Project";

    private final JavaMailSender javaMailSender;
    private final Parser markdownParser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${hermandad.mail.activation-url}")
    private String enlaceActivacionCuenta;


    public void enviarCorreoDesdeMarkdown(
            String destinatario,
            String asunto,
            String plantilla,
            Map<String, String> variables
    ) {
        long startTime = System.currentTimeMillis();
        String maskedEmail = SensitiveDataMasker.maskEmail(destinatario);
        LOGGER.info(
                "Iniciando envio de correo desde plantilla. destinatario={}, plantilla={}, asunto={}",
                maskedEmail,
                plantilla,
                asunto
        );

        try {
            LOGGER.debug("Cargando plantilla de correo. plantilla={}", plantilla);
            String markdown = cargarPlantilla(plantilla);
            LOGGER.debug("Plantilla de correo cargada. plantilla={}, longitudCaracteres={}", plantilla, markdown.length());

            LOGGER.debug("Sustituyendo variables de plantilla. plantilla={}, totalVariables={}", plantilla, variables.size());
            String markdownConVariables = reemplazarVariables(markdown, variables);

            LOGGER.debug("Renderizando plantilla de correo a HTML. plantilla={}", plantilla);
            String html = htmlRenderer.render(markdownParser.parse(markdownConVariables));

            LOGGER.debug("Construyendo mensaje de correo. destinatario={}, plantilla={}", maskedEmail, plantilla);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(html, true);

            LOGGER.debug("Iniciando envio SMTP. destinatario={}, plantilla={}", maskedEmail, plantilla);
            javaMailSender.send(message);
            LOGGER.info(
                    "Correo enviado correctamente. destinatario={}, plantilla={}, duracionMs={}",
                    maskedEmail,
                    plantilla,
                    System.currentTimeMillis() - startTime
            );
        } catch (IOException ex) {
            LOGGER.error(
                    "Error al cargar la plantilla de correo. destinatario={}, plantilla={}",
                    maskedEmail,
                    plantilla,
                    ex
            );
            throw new EmailSendingException("No se pudo cargar la plantilla de correo: " + plantilla, ex);
        } catch (MessagingException ex) {
            LOGGER.error(
                    "Error al construir el correo. destinatario={}, plantilla={}",
                    maskedEmail,
                    plantilla,
                    ex
            );
            throw new EmailSendingException("No se pudo construir el correo para: " + destinatario, ex);
        } catch (RuntimeException ex) {
            LOGGER.error(
                    "Error al enviar el correo. destinatario={}, plantilla={}",
                    maskedEmail,
                    plantilla,
                    ex
            );
            throw ex;
        }
    }

    public void enviarCorreoBienvenida(UsuarioEntity usuario) {
        String maskedEmail = SensitiveDataMasker.maskEmail(usuario.getCorreoElectronico());
        LOGGER.info(
                "Iniciando envio del correo de activacion. usuarioId={}, correo={}",
                usuario.getId(),
                maskedEmail
        );
        enviarCorreoDesdeMarkdown(
                usuario.getCorreoElectronico(),
                WELCOME_EMAIL_SUBJECT,
                WELCOME_EMAIL_TEMPLATE,
                Map.of("nombreUsuario", usuario.getNombreUsuario(), "enlaceActivacion",enlaceActivacionCuenta)
        );

        LOGGER.info(
                "Correo de activacion enviado correctamente. usuarioId={}, correo={}",
                usuario.getId(),
                maskedEmail
        );
    }

    public void enviarCorreoExpiracionTokenActivacion(UsuarioEntity usuario) {
        String maskedEmail = SensitiveDataMasker.maskEmail(usuario.getCorreoElectronico());
        LOGGER.info(
                "Iniciando envio del correo de expiración de token. usuarioId={}, correo={}",
                usuario.getId(),
                maskedEmail
        );

        enviarCorreoDesdeMarkdown(
                usuario.getCorreoElectronico(),
                TOKEN_EXPIRED_SUBJECT,
                TOKEN_EXPIRED_TEMPLATE,
                Map.of("nombreUsuario", usuario.getNombreUsuario(), "botonActivarCuenta", enlaceActivacionCuenta, "fechaExpiracion", usuario.getEstado().getActivationTokenExpiration().toString())
        );
        LOGGER.info(
                "Correo de expiración de token enviado correctamente. usuarioId={}, correo={}",
                usuario.getId(),
                maskedEmail
        );
    }

    private String cargarPlantilla(String plantilla) throws IOException {
        ClassPathResource resource = new ClassPathResource(EMAIL_TEMPLATES_PATH + plantilla);
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private String reemplazarVariables(String contenido, Map<String, String> variables) {
        String resultado = contenido;
        for (Map.Entry<String, String> variable : variables.entrySet()) {
            resultado = resultado.replace("{{" + variable.getKey() + "}}", Objects.toString(variable.getValue(), ""));
        }
        return resultado;
    }
}
