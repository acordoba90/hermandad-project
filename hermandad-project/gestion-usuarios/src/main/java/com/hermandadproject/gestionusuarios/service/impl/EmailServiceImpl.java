package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.config.properties.HermandadUserProperties;
import com.hermandadproject.gestionusuarios.exception.EmailSendingException;
import com.hermandadproject.gestionusuarios.logging.SensitiveDataMasker;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEstadoEntity;
import com.hermandadproject.gestionusuarios.repository.UsuarioEstadoRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private static final String EMAIL_TEMPLATES_PATH = "templates/emails/";
    private static final String WELCOME_EMAIL_TEMPLATE = "bienvenida.md";
    private static final String WELCOME_EMAIL_SUBJECT = "Bienvenido a Hermandad Project";
    private static final String TOKEN_EXPIRED_TEMPLATE = "tokenActivacionExpirado.md";
    private static final String TOKEN_EXPIRED_SUBJECT = "Nuevo enlace para activar tu cuenta de Hermandad Project";
    private static final String EMAIL_RESTORE_PASS = "Restablece tu contraseña de Hermandad Project.";
    private static final String PASSWORD_RESET_TEMPLATE = "restauracionPass.md";
    private static final String PASSWORD_RESET_SUBJECT = "Restablece tu contrasena de Hermandad Project";

    private final JavaMailSender javaMailSender;
    private final UsuarioEstadoRepository usuarioEstadoRepository;
    private final String enlaceActivacionCuenta;
    private final Parser markdownParser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
    private final HermandadUserProperties hermandadUserProperties;

    public EmailServiceImpl(
            JavaMailSender javaMailSender,
            UsuarioEstadoRepository usuarioEstadoRepository,
            @Value("${hermandad.mail.activation-url}") String enlaceActivacionCuenta,
            HermandadUserProperties hermandadUserProperties
    ) {
        this.javaMailSender = javaMailSender;
        this.usuarioEstadoRepository = usuarioEstadoRepository;
        this.enlaceActivacionCuenta = enlaceActivacionCuenta;
        this.hermandadUserProperties = hermandadUserProperties;
    }

    @Override
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

    @Override
    public void enviarCorreoBienvenida(UsuarioEntity usuario) {
        UsuarioEstadoEntity estado = obtenerEstadoActivacion(usuario);
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
                Map.of(
                        "nombreUsuario", usuario.getNombreUsuario(),
                        "enlaceActivacion", construirEnlaceActivacion(estado.getActivationToken())
                )
        );

        LOGGER.info(
                "Correo de activacion enviado correctamente. usuarioId={}, correo={}",
                usuario.getId(),
                maskedEmail
        );
    }

    @Override
    @Transactional
    public void enviarCorreoExpiracionTokenActivacion(String token) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("Iniciando envio de correo por expiracion de token de activacion. actor=ANONYMOUS");

        if (token == null || token.isBlank()) {
            LOGGER.warn("Envio de correo por expiracion rechazado: token de activacion vacio");
            throw new IllegalArgumentException("El token de activacion no puede estar vacio");
        }

        UsuarioEstadoEntity estado = usuarioEstadoRepository.findByActivationToken(token)
                .orElseThrow(() -> {
                    LOGGER.warn("Envio de correo por expiracion rechazado: token de activacion no encontrado");
                    return new IllegalArgumentException("No existe un estado de usuario para el token indicado");
                });

        UsuarioEntity usuario = estado.getUsuario();
        if (usuario == null) {
            LOGGER.warn("Envio de correo por expiracion rechazado: estado sin usuario asociado. estadoId={}", estado.getId());
            throw new IllegalStateException("El estado de usuario no tiene un usuario asociado");
        }

        Instant fechaExpiracion = estado.getActivationTokenExpiration();
        if (fechaExpiracion == null) {
            LOGGER.warn(
                    "Envio de correo por expiracion rechazado: token sin fecha de expiracion. usuarioId={}",
                    usuario.getId()
            );
            throw new IllegalStateException("El token de activacion no tiene fecha de expiracion");
        }

        String maskedEmail = SensitiveDataMasker.maskEmail(usuario.getCorreoElectronico());
        LOGGER.info(
                "Usuario localizado para correo por expiracion de token. usuarioId={}, correo={}, expiracion={}",
                usuario.getId(),
                maskedEmail,
                fechaExpiracion
        );

        // Generación y actualización del nuevo token de activación
        Instant now = Instant.now();
        Instant nuevaFechaExpiracionToken = now.plus(
                hermandadUserProperties.getActivation().getExpirationHours(),
                ChronoUnit.HOURS
        );
        String nuevoTokenGenerado = UUID.randomUUID().toString();
        estado.setActivationToken(nuevoTokenGenerado);
        estado.setActivationTokenExpiration(nuevaFechaExpiracionToken);
        LOGGER.debug(
                "Token de activacion generado. usuarioObjetivoId={}, expiracion={}",
                usuario.getId(),
                estado.getActivationTokenExpiration()
        );

        try {
            LOGGER.debug("Construyendo enlace de activacion para correo de expiracion. usuarioId={}", usuario.getId());
            enviarCorreoDesdeMarkdown(
                    usuario.getCorreoElectronico(),
                    TOKEN_EXPIRED_SUBJECT,
                    TOKEN_EXPIRED_TEMPLATE,
                    Map.of(
                            "nombreUsuario", usuario.getNombreUsuario(),
                            "botonActivarCuenta", construirEnlaceActivacion(nuevoTokenGenerado),
                            "fechaExpiracion", nuevaFechaExpiracionToken.toString()
                    )
            );
        } catch (RuntimeException exception) {
            LOGGER.error(
                    "Error al enviar correo por expiracion de token. usuarioId={}, correo={}",
                    usuario.getId(),
                    maskedEmail,
                    exception
            );
            throw exception;
        }

        usuarioEstadoRepository.save(estado);
        LOGGER.info(
                "Correo por expiracion de token enviado correctamente. usuarioId={}, correo={}, duracionMs={}",
                usuario.getId(),
                maskedEmail,
                System.currentTimeMillis() - startTime
        );
    }

    @Override
    public void enviarCorreoRestauracionContrasena(
            UsuarioEntity usuario,
            String enlaceRestauracion,
            String tiempoExpiracion
    ) {
        if (usuario == null) {
            LOGGER.warn("Envio de correo de restauracion rechazado: usuario null");
            throw new IllegalArgumentException("El usuario no puede ser null");
        }

        String maskedEmail = SensitiveDataMasker.maskEmail(usuario.getCorreoElectronico());
        LOGGER.info(
                "Iniciando envio del correo de restauracion de contrasena. usuarioId={}, correo={}",
                usuario.getId(),
                maskedEmail
        );
        enviarCorreoDesdeMarkdown(
                usuario.getCorreoElectronico(),
                PASSWORD_RESET_SUBJECT,
                PASSWORD_RESET_TEMPLATE,
                Map.of(
                        "nombreUsuario", usuario.getNombreUsuario(),
                        "enlaceRestauracion", enlaceRestauracion,
                        "tiempoExpiracion", tiempoExpiracion
                )
        );
        LOGGER.info(
                "Correo de restauracion de contrasena enviado correctamente. usuarioId={}, correo={}",
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

    private String construirEnlaceActivacion(String token) {
        return UriComponentsBuilder.fromUriString(enlaceActivacionCuenta)
                .queryParam("token", token)
                .build()
                .toUriString();
    }

    private UsuarioEstadoEntity obtenerEstadoActivacion(UsuarioEntity usuario) {
        if (usuario == null) {
            LOGGER.warn("Envio de correo de activacion rechazado: usuario null");
            throw new IllegalArgumentException("El usuario no puede ser null");
        }

        if (usuario.getId() == null) {
            LOGGER.warn("Envio de correo de activacion rechazado: usuario sin identificador");
            throw new IllegalArgumentException("El usuario debe tener un identificador");
        }

        LOGGER.debug("Buscando estado de activacion para envio de correo. usuarioId={}", usuario.getId());
        return usuarioEstadoRepository.findByUsuarioId(usuario.getId())
                .filter(estado -> estado.getActivationToken() != null)
                .orElseThrow(() -> {
                    LOGGER.warn("Envio de correo de activacion rechazado: token no encontrado. usuarioId={}", usuario.getId());
                    return new IllegalStateException("No existe un token de activacion para el usuario indicado");
                });
    }
}
