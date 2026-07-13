package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.config.properties.HermandadUserProperties;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEstadoEntity;
import com.hermandadproject.gestionusuarios.model.enums.AccountStatusEnum;
import com.hermandadproject.gestionusuarios.repository.UserRepository;
import com.hermandadproject.gestionusuarios.repository.UsuarioEstadoRepository;
import com.hermandadproject.gestionusuarios.service.UsuarioEstadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioEstadoServiceImpl implements UsuarioEstadoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioEstadoServiceImpl.class);

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
        UUID usuarioId = usuario == null ? null : usuario.getId();
        LOGGER.debug("Iniciando creacion de estado inicial. usuarioObjetivoId={}", usuarioId);
        if (usuario == null) {
            LOGGER.warn("Creacion de estado inicial rechazada: usuario null");
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        if (usuario.getId() == null) {
            LOGGER.warn("Creacion de estado inicial rechazada: usuario sin identificador");
            throw new IllegalArgumentException("El usuario debe tener un identificador antes de crear su estado");
        }
        if (!userRepository.existsById(usuario.getId())) {
            LOGGER.warn("Creacion de estado inicial rechazada: usuario inexistente. usuarioObjetivoId={}", usuario.getId());
            throw new IllegalArgumentException("No existe el usuario indicado para crear su estado");
        }
        if (usuarioEstadoRepository.existsByUsuarioId(usuario.getId())) {
            LOGGER.warn("Creacion de estado inicial rechazada: estado ya existente. usuarioObjetivoId={}", usuario.getId());
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
        LOGGER.debug(
                "Token de activacion generado. usuarioObjetivoId={}, expiracion={}",
                usuario.getId(),
                estado.getActivationTokenExpiration()
        );
        estado.setFailedLoginAttempts(0);
        estado.setPasswordChangedAt(now);

        UsuarioEstadoEntity saved = usuarioEstadoRepository.save(estado);
        LOGGER.info(
                "Estado inicial creado correctamente. usuarioObjetivoId={}, estado={}, estadoId={}",
                usuario.getId(),
                saved.getAccountStatus(),
                saved.getId()
        );
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEstadoEntity buscarPorToken(String token) {
        LOGGER.debug("Buscando usuario asociado al token de activacion");
        if (token == null || token.isBlank()) {
            LOGGER.warn("Busqueda de token rechazada: token de activacion vacio");
            throw new IllegalArgumentException("El token de activacion no puede estar vacio");
        }

        return usuarioEstadoRepository.findByActivationToken(token)
                .map(estado -> {
                    LOGGER.debug(
                            "Token asociado a usuario. actor=ANONYMOUS, usuarioObjetivoId={}, estadoActual={}",
                            estado.getUsuario().getId(),
                            estado.getAccountStatus()
                    );
                    return estado;
                })
                .orElseThrow(() -> {
                    LOGGER.warn("Activacion rechazada: token de activacion no encontrado");
                    return new IllegalArgumentException("No existe un estado de usuario para el token indicado");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioEstadoEntity buscarPorUsuarioId(UUID usuarioId) {
        LOGGER.debug("Buscando estado por usuario. usuarioObjetivoId={}", usuarioId);
        validarUsuarioId(usuarioId);
        return buscarEstadoPorUsuarioId(usuarioId);
    }

    @Override
    @Transactional
    public void activarCuenta(String token) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("Solicitud publica de activacion de cuenta. actor=ANONYMOUS");
        UsuarioEstadoEntity estado = buscarPorToken(token);
        UUID usuarioId = estado.getUsuario().getId();
        AccountStatusEnum estadoAnterior = estado.getAccountStatus();
        LOGGER.debug(
                "Usuario localizado para activacion. usuarioObjetivoId={}, estadoActual={}",
                usuarioId,
                estadoAnterior
        );
        Instant expiration = estado.getActivationTokenExpiration();
        if (expiration == null || Instant.now().isAfter(expiration)) {
            LOGGER.warn(
                    "Activacion rechazada: token caducado. usuarioObjetivoId={}, expiracion={}",
                    usuarioId,
                    expiration
            );
            throw new IllegalStateException("El token de activacion ha expirado");
        }

        LOGGER.info(
                "Actualizando estado de usuario. usuarioObjetivoId={}, estadoAnterior={}, estadoNuevo={}",
                usuarioId,
                estadoAnterior,
                AccountStatusEnum.ACTIVE
        );
        estado.setAccountStatus(AccountStatusEnum.ACTIVE);
        estado.setEmailVerified(true);
        LOGGER.debug("Invalidando token de activacion utilizado. usuarioObjetivoId={}", usuarioId);
        estado.setActivationToken(null);
        estado.setActivationTokenExpiration(null);
        usuarioEstadoRepository.save(estado);
        LOGGER.info(
                "Cuenta activada correctamente. usuarioObjetivoId={}, emailVerificado=true, duracionMs={}",
                usuarioId,
                System.currentTimeMillis() - startTime
        );
    }

    @Override
    @Transactional
    public void registrarLogin(UUID usuarioId) {
        LOGGER.debug("Registrando login correcto. usuarioObjetivoId={}", usuarioId);
        UsuarioEstadoEntity estado = buscarEstadoPorUsuarioId(usuarioId);
        if (estado.getAccountStatus() == AccountStatusEnum.LOCKED) {
            // Antes de rechazar el login se comprueba si el bloqueo temporal ya vencio.
            LOGGER.warn(
                    "Login detectado sobre cuenta bloqueada. usuarioObjetivoId={}, lockedUntil={}",
                    usuarioId,
                    estado.getLockedUntil()
            );
            desbloquearSiProcede(usuarioId);
            estado = buscarEstadoPorUsuarioId(usuarioId);
            if (estado.getAccountStatus() == AccountStatusEnum.LOCKED) {
                LOGGER.warn("Login rechazado: cuenta bloqueada temporalmente. usuarioObjetivoId={}", usuarioId);
                throw new IllegalStateException("La cuenta esta bloqueada temporalmente");
            }
        }

        Instant now = Instant.now();
        estado.setLastLogin(now);
        estado.setLastActivity(now);
        estado.setFailedLoginAttempts(0);
        usuarioEstadoRepository.save(estado);
        LOGGER.info("Login registrado correctamente. usuarioObjetivoId={}, lastLogin={}", usuarioId, now);
    }

    @Override
    @Transactional
    public void registrarActividad(UUID usuarioId) {
        LOGGER.debug("Registrando actividad de usuario. usuarioObjetivoId={}", usuarioId);
        UsuarioEstadoEntity estado = buscarEstadoPorUsuarioId(usuarioId);
        estado.setLastActivity(Instant.now());
        usuarioEstadoRepository.save(estado);
        LOGGER.debug("Actividad registrada. usuarioObjetivoId={}, lastActivity={}", usuarioId, estado.getLastActivity());
    }

    @Override
    @Transactional
    public void incrementarIntentoFallido(UUID usuarioId) {
        LOGGER.debug("Incrementando intento fallido de login. usuarioObjetivoId={}", usuarioId);
        UsuarioEstadoEntity estado = buscarEstadoPorUsuarioId(usuarioId);
        int failedLoginAttempts = obtenerIntentosFallidos(estado) + 1;
        estado.setFailedLoginAttempts(failedLoginAttempts);

        // El bloqueo local encapsula la politica actual y podra sustituirse por Keycloak.
        if (failedLoginAttempts >= hermandadUserProperties.getLogin().getMaxFailedAttempts()) {
            AccountStatusEnum previousStatus = estado.getAccountStatus();
            estado.setAccountStatus(AccountStatusEnum.LOCKED);
            estado.setLockedUntil(Instant.now().plus(
                    hermandadUserProperties.getLogin().getLockMinutes(),
                    ChronoUnit.MINUTES
            ));
            LOGGER.warn(
                    "Cuenta bloqueada por intentos fallidos. usuarioObjetivoId={}, intentos={}, estadoAnterior={}, lockedUntil={}",
                    usuarioId,
                    failedLoginAttempts,
                    previousStatus,
                    estado.getLockedUntil()
            );
        }

        usuarioEstadoRepository.save(estado);
        LOGGER.debug(
                "Intento fallido registrado. usuarioObjetivoId={}, intentos={}",
                usuarioId,
                failedLoginAttempts
        );
    }

    @Override
    @Transactional
    public void desbloquearSiProcede(UUID usuarioId) {
        LOGGER.debug("Evaluando desbloqueo de cuenta. usuarioObjetivoId={}", usuarioId);
        UsuarioEstadoEntity estado = buscarEstadoPorUsuarioId(usuarioId);
        Instant lockedUntil = estado.getLockedUntil();
        if (lockedUntil != null && Instant.now().isAfter(lockedUntil)) {
            AccountStatusEnum previousStatus = estado.getAccountStatus();
            estado.setAccountStatus(AccountStatusEnum.ACTIVE);
            estado.setFailedLoginAttempts(0);
            estado.setLockedUntil(null);
            usuarioEstadoRepository.save(estado);
            LOGGER.info(
                    "Cuenta desbloqueada correctamente. usuarioObjetivoId={}, estadoAnterior={}, estadoNuevo={}",
                    usuarioId,
                    previousStatus,
                    AccountStatusEnum.ACTIVE
            );
        }
    }

    @Override
    @Transactional
    public void desactivarUsuariosInactivos(int dias) {
        LOGGER.info("Iniciando desactivacion de usuarios inactivos. dias={}", dias);
        if (dias <= 0) {
            LOGGER.warn("Desactivacion de usuarios inactivos rechazada: dias invalidos. dias={}", dias);
            throw new IllegalArgumentException("Los dias de inactividad deben ser mayores que cero");
        }

        Instant fechaLimite = Instant.now().minus(dias, ChronoUnit.DAYS);
        List<UsuarioEstadoEntity> estados = usuarioEstadoRepository.findByAccountStatusAndLastLoginBefore(
                AccountStatusEnum.ACTIVE,
                fechaLimite
        );

        estados.forEach(estado -> estado.setAccountStatus(AccountStatusEnum.INACTIVE));
        usuarioEstadoRepository.saveAll(estados);
        LOGGER.info(
                "Desactivacion de usuarios inactivos completada. dias={}, fechaLimite={}, total={}",
                dias,
                fechaLimite,
                estados.size()
        );
    }

    private UsuarioEstadoEntity buscarEstadoPorUsuarioId(UUID usuarioId) {
        validarUsuarioId(usuarioId);
        return usuarioEstadoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> {
                    LOGGER.warn("Estado de usuario no encontrado. usuarioObjetivoId={}", usuarioId);
                    return new IllegalArgumentException("No existe un estado para el usuario indicado");
                });
    }

    private void validarUsuarioId(UUID usuarioId) {
        if (usuarioId == null) {
            LOGGER.warn("Operacion de estado rechazada: usuarioObjetivoId null");
            throw new IllegalArgumentException("El identificador del usuario no puede ser null");
        }
    }

    private int obtenerIntentosFallidos(UsuarioEstadoEntity estado) {
        return estado.getFailedLoginAttempts() == null ? 0 : estado.getFailedLoginAttempts();
    }
}
