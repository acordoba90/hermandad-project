package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.exception.EmailSendingException;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_TEMPLATES_PATH = "templates/emails/";
    private static final String WELCOME_EMAIL_TEMPLATE = "bienvenida.md";
    private static final String WELCOME_EMAIL_SUBJECT = "Bienvenido a Hermandad Project";

    private JavaMailSender javaMailSender;
    private final Parser markdownParser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();


    public void enviarCorreoDesdeMarkdown(
            String destinatario,
            String asunto,
            String plantilla,
            Map<String, String> variables
    ) {
        try {
            String markdown = cargarPlantilla(plantilla);
            String markdownConVariables = reemplazarVariables(markdown, variables);
            String html = htmlRenderer.render(markdownParser.parse(markdownConVariables));

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (IOException ex) {
            throw new EmailSendingException("No se pudo cargar la plantilla de correo: " + plantilla, ex);
        } catch (MessagingException ex) {
            throw new EmailSendingException("No se pudo construir el correo para: " + destinatario, ex);
        }
    }

    public void enviarCorreoBienvenida(UsuarioEntity usuario) {
        enviarCorreoDesdeMarkdown(
                usuario.getCorreoElectronico(),
                WELCOME_EMAIL_SUBJECT,
                WELCOME_EMAIL_TEMPLATE,
                Map.of("nombreUsuario", usuario.getNombreUsuario())
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
