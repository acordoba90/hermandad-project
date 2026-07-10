package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.config.properties.HermandadUserProperties;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEstadoEntity;
import com.hermandadproject.gestionusuarios.model.enums.AccountStatusEnum;
import com.hermandadproject.gestionusuarios.repository.UserRepository;
import com.hermandadproject.gestionusuarios.repository.UsuarioEstadoRepository;
import com.hermandadproject.gestionusuarios.service.UsuarioEstadoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioEstadoServiceImpl implements UsuarioEstadoService {

    private final UsuarioEstadoRepository usuarioEstadoRepository;
    private final UserRepository userRepository;
    private final HermandadUserProperties hermandadUserProperties;

    public UsuarioEstadoServiceImpl(
            UsuarioEstadoRepository usuarioEstadoRepository,
            UserRepository userRepository,
            HermandadUserProperties hermandadUserProperties
    ) {
        this.usuarioEstadoRepository = usuarioEstadoRepository;
        this.userRepository = userRepository;
        this.hermandadUserProperties = hermandadUserProperties;
    }

    @Override
    @Transactional
    public UsuarioEstadoEntity crearEstadoInicial(UsuarioEntity usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario debe tener un identificador antes de crear su estado");
        }
        if (!userRepository.existsById(usuario.getId())) {
            throw new IllegalArgumentException("No existe el usuario indicado para crear su estado");
        }
        if (usuarioEstadoRepository.existsByUsuarioId(usuario.getId())) {
            throw new IllegalStateException("Ya existe un estado para el usuario indicado");
        }

        // El token queda aislado en esta capa para facilitar una futura delegacion en Keycloak.
        Instant now = Instant.now();
        UsuarioEstadoEntity estado = new UsuarioEstadoEntity();
        estado.setUsuario(usuario);
        estado.setAccountStatus(AccountStatusEnum.PENDING);
        estado.setEmailVerified(false);
        estado.setActivationToken(UUID.randomUUID().toString());
        estado.setActivationTokenExpiration(now.plus(
                hermandadUserProperties.getActivation().getExpirationHours(),
                ChronoUnit.HOURS
        ));
        estado.setFailedLoginAttempts(0);
        estado.setPasswordChangedAt(now);

        return usuarioEstadoRepository.save(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEstadoEntity buscarPorToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("El token de activacion no puede estar vacio");
        }

        return usuarioEstadoRepository.findByActivationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("No existe un estado de usuario para el token indicado"));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEstadoEntity buscarPorUsuarioId(UUID usuarioId) {
        validarUsuarioId(usuarioId);
        return buscarEstadoPorUsuarioId(usuarioId);
    }

    @Override
    @Transactional
    public void activarCuenta(String token) {
        UsuarioEstadoEntity estado = buscarPorToken(token);
        Instant expiration = estado.getActivationTokenExpiration();
        if (expiration == null || Instant.now().isAfter(expiration)) {
            throw new IllegalStateException("El token de activacion ha expirado");
        }

        estado.setAccountStatus(AccountStatusEnum.ACTIVE);
        estado.setEmailVerified(true);
        estado.setActivationToken(null);
        estado.setActivationTokenExpiration(null);
        usuarioEstadoRepository.save(estado);
    }

    @Override
    @Transactional
    public void registrarLogin(UUID usuarioId) {
        UsuarioEstadoEntity estado = buscarEstadoPorUsuarioId(usuarioId);
        if (estado.getAccountStatus() == AccountStatusEnum.LOCKED) {
            // Antes de rechazar el login se comprueba si el bloqueo temporal ya vencio.
            desbloquearSiProcede(usuarioId);
            estado = buscarEstadoPorUsuarioId(usuarioId);
            if (estado.getAccountStatus() == AccountStatusEnum.LOCKED) {
                throw new IllegalStateException("La cuenta esta bloqueada temporalmente");
            }
        }

        Instant now = Instant.now();
        estado.setLastLogin(now);
        estado.setLastActivity(now);
        estado.setFailedLoginAttempts(0);
        usuarioEstadoRepository.save(estado);
    }

    @Override
    @Transactional
    public void registrarActividad(UUID usuarioId) {
        UsuarioEstadoEntity estado = buscarEstadoPorUsuarioId(usuarioId);
        estado.setLastActivity(Instant.now());
        usuarioEstadoRepository.save(estado);
    }

    @Override
    @Transactional
    public void incrementarIntentoFallido(UUID usuarioId) {
        UsuarioEstadoEntity estado = buscarEstadoPorUsuarioId(usuarioId);
        int failedLoginAttempts = obtenerIntentosFallidos(estado) + 1;
        estado.setFailedLoginAttempts(failedLoginAttempts);

        // El bloqueo local encapsula la politica actual y podra sustituirse por Keycloak.
        if (failedLoginAttempts >= hermandadUserProperties.getLogin().getMaxFailedAttempts()) {
            estado.setAccountStatus(AccountStatusEnum.LOCKED);
            estado.setLockedUntil(Instant.now().plus(
                    hermandadUserProperties.getLogin().getLockMinutes(),
                    ChronoUnit.MINUTES
            ));
        }

        usuarioEstadoRepository.save(estado);
    }

    @Override
    @Transactional
    public void desbloquearSiProcede(UUID usuarioId) {
        UsuarioEstadoEntity estado = buscarEstadoPorUsuarioId(usuarioId);
        Instant lockedUntil = estado.getLockedUntil();
        if (lockedUntil != null && Instant.now().isAfter(lockedUntil)) {
            estado.setAccountStatus(AccountStatusEnum.ACTIVE);
            estado.setFailedLoginAttempts(0);
            estado.setLockedUntil(null);
            usuarioEstadoRepository.save(estado);
        }
    }

    @Override
    @Transactional
    public void desactivarUsuariosInactivos(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los dias de inactividad deben ser mayores que cero");
        }

        Instant fechaLimite = Instant.now().minus(dias, ChronoUnit.DAYS);
        List<UsuarioEstadoEntity> estados = usuarioEstadoRepository.findByAccountStatusAndLastLoginBefore(
                AccountStatusEnum.ACTIVE,
                fechaLimite
        );

        estados.forEach(estado -> estado.setAccountStatus(AccountStatusEnum.INACTIVE));
        usuarioEstadoRepository.saveAll(estados);
    }

    private UsuarioEstadoEntity buscarEstadoPorUsuarioId(UUID usuarioId) {
        validarUsuarioId(usuarioId);
        return usuarioEstadoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("No existe un estado para el usuario indicado"));
    }

    private void validarUsuarioId(UUID usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("El identificador del usuario no puede ser null");
        }
    }

    private int obtenerIntentosFallidos(UsuarioEstadoEntity estado) {
        return estado.getFailedLoginAttempts() == null ? 0 : estado.getFailedLoginAttempts();
    }
}
