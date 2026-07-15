package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.config.properties.HermandadUserProperties;
import com.hermandadproject.gestionusuarios.model.dto.ConfirmacionRestauracionContrasenaRequest;
import com.hermandadproject.gestionusuarios.model.dto.SolicitudRestauracionContrasenaRequest;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEstadoEntity;
import com.hermandadproject.gestionusuarios.model.enums.AccountStatusEnum;
import com.hermandadproject.gestionusuarios.repository.UserRepository;
import com.hermandadproject.gestionusuarios.repository.UsuarioEstadoRepository;
import com.hermandadproject.gestionusuarios.service.EmailService;
import com.hermandadproject.gestionusuarios.service.UsuarioEstadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplPasswordResetTest {

    private UserRepository userRepository;
    private UsuarioEstadoRepository usuarioEstadoRepository;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        usuarioEstadoRepository = mock(UsuarioEstadoRepository.class);
        emailService = mock(EmailService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        HermandadUserProperties properties = new HermandadUserProperties();
        properties.getPasswordReset().setTokenExpirationMinutes(30);
        properties.getPassword().setMinLength(8);
        properties.getPassword().setRequireUppercase(true);
        properties.getPassword().setRequireNumber(true);
        properties.getPassword().setRequireSpecialCharacter(true);

        userService = new UserServiceImpl(
                userRepository,
                null,
                passwordEncoder,
                mock(UsuarioEstadoService.class),
                usuarioEstadoRepository,
                emailService,
                properties,
                "http://localhost:5173/restaurar-contrasena"
        );
    }

    @Test
    void solicitarRestauracionGeneraTokenYPersisteAntesDeEnviarCorreo() {
        UsuarioEntity usuario = usuario();
        UsuarioEstadoEntity estado = estado(usuario, AccountStatusEnum.ACTIVE);
        estado.setTokenRestauracionContrasena("token-anterior");

        when(userRepository.findByCorreoElectronicoIgnoreCase("usuario@correo.com")).thenReturn(Optional.of(usuario));
        when(usuarioEstadoRepository.findByUsuarioId(usuario.getId())).thenReturn(Optional.of(estado));
        when(usuarioEstadoRepository.saveAndFlush(estado)).thenReturn(estado);

        userService.solicitarRestauracionContrasena(
                new SolicitudRestauracionContrasenaRequest("  Usuario@Correo.com  ")
        );

        assertThat(estado.getTokenRestauracionContrasena()).isNotBlank();
        assertThat(estado.getTokenRestauracionContrasena()).isNotEqualTo("token-anterior");
        assertThat(UUID.fromString(estado.getTokenRestauracionContrasena())).isNotNull();
        assertThat(estado.getExpiracionTokenRestauracionContrasena()).isAfter(Instant.now().plusSeconds(20 * 60));

        ArgumentCaptor<String> enlaceCaptor = ArgumentCaptor.forClass(String.class);
        verify(usuarioEstadoRepository).saveAndFlush(estado);
        verify(emailService).enviarCorreoRestauracionContrasena(eq(usuario), enlaceCaptor.capture(), eq("30 minutos"));
        assertThat(enlaceCaptor.getValue())
                .startsWith("http://localhost:5173/restaurar-contrasena?token=")
                .contains(estado.getTokenRestauracionContrasena());
    }

    @Test
    void solicitarRestauracionNoRevelaNiEnviaCorreoSiElUsuarioNoExiste() {
        when(userRepository.findByCorreoElectronicoIgnoreCase("desconocido@correo.com")).thenReturn(Optional.empty());

        userService.solicitarRestauracionContrasena(
                new SolicitudRestauracionContrasenaRequest("desconocido@correo.com")
        );

        verify(usuarioEstadoRepository, never()).saveAndFlush(any());
        verify(emailService, never()).enviarCorreoRestauracionContrasena(any(), any(), any());
    }

    @Test
    void solicitarRestauracionNoGeneraTokenParaEstadosNoPermitidos() {
        UsuarioEntity usuario = usuario();
        UsuarioEstadoEntity estado = estado(usuario, AccountStatusEnum.LOCKED);
        when(userRepository.findByCorreoElectronicoIgnoreCase(usuario.getCorreoElectronico())).thenReturn(Optional.of(usuario));
        when(usuarioEstadoRepository.findByUsuarioId(usuario.getId())).thenReturn(Optional.of(estado));

        userService.solicitarRestauracionContrasena(
                new SolicitudRestauracionContrasenaRequest(usuario.getCorreoElectronico())
        );

        assertThat(estado.getTokenRestauracionContrasena()).isNull();
        verify(usuarioEstadoRepository, never()).saveAndFlush(any());
        verify(emailService, never()).enviarCorreoRestauracionContrasena(any(), any(), any());
    }

    @Test
    void solicitarRestauracionNoGeneraTokenParaEstadoDeleted() {
        UsuarioEntity usuario = usuario();
        UsuarioEstadoEntity estado = estado(usuario, AccountStatusEnum.DELETED);
        when(userRepository.findByCorreoElectronicoIgnoreCase(usuario.getCorreoElectronico())).thenReturn(Optional.of(usuario));
        when(usuarioEstadoRepository.findByUsuarioId(usuario.getId())).thenReturn(Optional.of(estado));

        userService.solicitarRestauracionContrasena(
                new SolicitudRestauracionContrasenaRequest(usuario.getCorreoElectronico())
        );

        assertThat(estado.getTokenRestauracionContrasena()).isNull();
        verify(usuarioEstadoRepository, never()).saveAndFlush(any());
        verify(emailService, never()).enviarCorreoRestauracionContrasena(any(), any(), any());
    }

    @Test
    void solicitarRestauracionPropagaErrorDeCorreoParaPermitirRollback() {
        UsuarioEntity usuario = usuario();
        UsuarioEstadoEntity estado = estado(usuario, AccountStatusEnum.ACTIVE);
        when(userRepository.findByCorreoElectronicoIgnoreCase(usuario.getCorreoElectronico())).thenReturn(Optional.of(usuario));
        when(usuarioEstadoRepository.findByUsuarioId(usuario.getId())).thenReturn(Optional.of(estado));
        when(usuarioEstadoRepository.saveAndFlush(estado)).thenReturn(estado);
        doThrow(new IllegalStateException("smtp"))
                .when(emailService)
                .enviarCorreoRestauracionContrasena(eq(usuario), any(), eq("30 minutos"));

        assertThatThrownBy(() -> userService.solicitarRestauracionContrasena(
                new SolicitudRestauracionContrasenaRequest(usuario.getCorreoElectronico())
        )).isInstanceOf(IllegalStateException.class)
                .hasMessage("smtp");

        verify(usuarioEstadoRepository).saveAndFlush(estado);
    }

    @Test
    void confirmarRestauracionActualizaContrasenaActivaCuentaYEliminaToken() {
        UsuarioEntity usuario = usuario();
        UsuarioEstadoEntity estado = estado(usuario, AccountStatusEnum.PENDING);
        estado.setTokenRestauracionContrasena("token-valido");
        estado.setExpiracionTokenRestauracionContrasena(Instant.now().plusSeconds(300));

        when(usuarioEstadoRepository.findByTokenRestauracionContrasena("token-valido")).thenReturn(Optional.of(estado));
        when(passwordEncoder.encode("NuevaPass1!")).thenReturn("hash");

        userService.confirmarRestauracionContrasena(
                new ConfirmacionRestauracionContrasenaRequest("token-valido", "NuevaPass1!", "NuevaPass1!")
        );

        assertThat(usuario.getHashContrasena()).isEqualTo("hash");
        assertThat(estado.getPasswordChangedAt()).isNotNull();
        assertThat(estado.getAccountStatus()).isEqualTo(AccountStatusEnum.ACTIVE);
        assertThat(estado.getTokenRestauracionContrasena()).isNull();
        assertThat(estado.getExpiracionTokenRestauracionContrasena()).isNull();
        verify(userRepository).save(usuario);
        verify(usuarioEstadoRepository).save(estado);
    }

    @Test
    void confirmarRestauracionRechazaTokenExpirado() {
        UsuarioEntity usuario = usuario();
        UsuarioEstadoEntity estado = estado(usuario, AccountStatusEnum.ACTIVE);
        estado.setTokenRestauracionContrasena("token-expirado");
        estado.setExpiracionTokenRestauracionContrasena(Instant.now());
        when(usuarioEstadoRepository.findByTokenRestauracionContrasena("token-expirado")).thenReturn(Optional.of(estado));

        assertThatThrownBy(() -> userService.confirmarRestauracionContrasena(
                new ConfirmacionRestauracionContrasenaRequest("token-expirado", "NuevaPass1!", "NuevaPass1!")
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El enlace de restauracion ha expirado");

        verify(userRepository, never()).save(any());
    }

    @Test
    void confirmarRestauracionRechazaEstadoActualNoPermitido() {
        UsuarioEntity usuario = usuario();
        UsuarioEstadoEntity estado = estado(usuario, AccountStatusEnum.DELETED);
        estado.setTokenRestauracionContrasena("token-valido");
        estado.setExpiracionTokenRestauracionContrasena(Instant.now().plusSeconds(300));
        when(usuarioEstadoRepository.findByTokenRestauracionContrasena("token-valido")).thenReturn(Optional.of(estado));

        assertThatThrownBy(() -> userService.confirmarRestauracionContrasena(
                new ConfirmacionRestauracionContrasenaRequest("token-valido", "NuevaPass1!", "NuevaPass1!")
        )).isInstanceOf(IllegalStateException.class)
                .hasMessage("No se puede restaurar la contrasena de esta cuenta");

        verify(userRepository, never()).save(any());
    }

    @Test
    void confirmarRestauracionValidaPoliticaYConfirmacion() {
        assertThatThrownBy(() -> userService.confirmarRestauracionContrasena(
                new ConfirmacionRestauracionContrasenaRequest("token", "NuevaPass1!", "OtraPass1!")
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Las contrasenas introducidas no coinciden");

        assertThatThrownBy(() -> userService.confirmarRestauracionContrasena(
                new ConfirmacionRestauracionContrasenaRequest("token", "simple", "simple")
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contrasena no cumple los requisitos de seguridad");
    }

    private UsuarioEntity usuario() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(UUID.randomUUID());
        usuario.setNombreUsuario("usuario");
        usuario.setCorreoElectronico("usuario@correo.com");
        usuario.setHashContrasena("old-hash");
        return usuario;
    }

    private UsuarioEstadoEntity estado(UsuarioEntity usuario, AccountStatusEnum status) {
        UsuarioEstadoEntity estado = new UsuarioEstadoEntity();
        estado.setId(UUID.randomUUID());
        estado.setUsuario(usuario);
        estado.setAccountStatus(status);
        return estado;
    }
}
