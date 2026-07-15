package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.config.properties.HermandadUserProperties;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.repository.UsuarioEstadoRepository;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Properties;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmailServiceImplPasswordResetTest {

    @Test
    void enviarCorreoRestauracionContrasenaUsaPlantillaMarkdownYSustituyeMarcadores() throws Exception {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        MimeMessage message = new MimeMessage(Session.getInstance(new Properties()));
        when(mailSender.createMimeMessage()).thenReturn(message);
        EmailServiceImpl emailService = new EmailServiceImpl(
                mailSender,
                mock(UsuarioEstadoRepository.class),
                "http://localhost:5173/activar-cuenta",
                new HermandadUserProperties()
        );
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(UUID.randomUUID());
        usuario.setNombreUsuario("Antonio");
        usuario.setCorreoElectronico("antonio@correo.com");

        emailService.enviarCorreoRestauracionContrasena(
                usuario,
                "http://localhost:5173/restaurar-contrasena?token=abc",
                "30 minutos"
        );

        verify(mailSender).send(message);
        assertThat(message.getSubject()).isEqualTo("Restablece tu contrasena de Hermandad Project");
        Multipart content = (Multipart) message.getContent();
        String html = content.getBodyPart(0).getContent().toString();
        assertThat(html)
                .contains("Antonio")
                .contains("http://localhost:5173/restaurar-contrasena?token=abc")
                .contains("30 minutos")
                .doesNotContain("{{nombreUsuario}}")
                .doesNotContain("{{enlaceRestauracion}}")
                .doesNotContain("{{tiempoExpiracion}}");
    }
}
