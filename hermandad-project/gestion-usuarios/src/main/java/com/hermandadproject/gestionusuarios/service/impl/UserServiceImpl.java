package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.config.properties.HermandadUserProperties;
import com.hermandadproject.gestionusuarios.exception.InvalidUserCredentialsException;
import com.hermandadproject.gestionusuarios.exception.UserAlreadyExistsException;
import com.hermandadproject.gestionusuarios.exception.UserNotFoundException;
import com.hermandadproject.gestionusuarios.logging.SensitiveDataMasker;
import com.hermandadproject.gestionusuarios.mapper.UserMapper;
import com.hermandadproject.gestionusuarios.model.dto.ConfirmacionRestauracionContrasenaRequest;
import com.hermandadproject.gestionusuarios.model.dto.SolicitudRestauracionContrasenaRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserValidationRequest;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEstadoEntity;
import com.hermandadproject.gestionusuarios.model.enums.AccountStatusEnum;
import com.hermandadproject.gestionusuarios.model.enums.UserRoleEnum;
import com.hermandadproject.gestionusuarios.repository.UserRepository;
import com.hermandadproject.gestionusuarios.repository.UsuarioEstadoRepository;
import com.hermandadproject.gestionusuarios.service.EmailService;
import com.hermandadproject.gestionusuarios.service.UserService;
import com.hermandadproject.gestionusuarios.service.UsuarioEstadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String INVALID_CREDENTIALS_MESSAGE = "El correo o la contraseña introducida no son correctos";
    private static final String USUARIO_NO_ACTIVADO = "El usuario no se encuentra activado, por favor, revise su correo electrónico";
    private static final String USUARIO_INACTIVO = "El usuario ha sido dado de baja por inactividad";
    private static final String USUARIO_BLOQUEADO = "La cuenta esta bloqueada temporalmente";
    private static final String PASSWORD_RESET_INVALID_LINK = "El enlace de restauracion no es valido";
    private static final String PASSWORD_RESET_EXPIRED_LINK = "El enlace de restauracion ha expirado";
    private static final String PASSWORD_RESET_FORBIDDEN_ACCOUNT = "No se puede restaurar la contrasena de esta cuenta";
    private static final String PASSWORD_RESET_PASSWORD_MISMATCH = "Las contrasenas introducidas no coinciden";
    private static final String PASSWORD_RESET_INVALID_PASSWORD = "La contrasena no cumple los requisitos de seguridad";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioEstadoService usuarioEstadoService;
    private final UsuarioEstadoRepository usuarioEstadoRepository;
    private final EmailService emailService;
    private final HermandadUserProperties hermandadUserProperties;
    private final String enlaceRestauracionContrasena;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            UsuarioEstadoService usuarioEstadoService,
            UsuarioEstadoRepository usuarioEstadoRepository,
            EmailService emailService,
            HermandadUserProperties hermandadUserProperties,
            @Value("${hermandad.mail.reset-password-url}") String enlaceRestauracionContrasena
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.usuarioEstadoService = usuarioEstadoService;
        this.usuarioEstadoRepository = usuarioEstadoRepository;
        this.emailService = emailService;
        this.hermandadUserProperties = hermandadUserProperties;
        this.enlaceRestauracionContrasena = enlaceRestauracionContrasena;
    }

    @Override
    public List<UserResponse> getAll() {
        LOGGER.debug("Consultando todos los usuarios");
        List<UserResponse> users = userRepository.findAll().stream().map(userMapper::toResponse).toList();
        LOGGER.debug("Consulta de usuarios completada. total={}", users.size());
        return users;
    }

    @Override
    public UserResponse getById(UUID id) {
        LOGGER.debug("Buscando usuario por identificador. usuarioObjetivoId={}", id);
        UsuarioEntity entity = userRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Usuario no encontrado. usuarioObjetivoId={}", id);
                    return new UserNotFoundException("Usuario no encontrado");
                });
        LOGGER.debug(
                "Usuario localizado. usuarioObjetivoId={}, nombreUsuario={}",
                entity.getId(),
                entity.getNombreUsuario()
        );
        return userMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public UserResponse create(UserCreateRequest request) {
        long startTime = System.currentTimeMillis();
        String maskedEmail = SensitiveDataMasker.maskEmail(request.correoElectronico());
        LOGGER.info(
                "Iniciando registro de usuario. actor=ANONYMOUS, nombreUsuario={}, correo={}",
                request.nombreUsuario(),
                maskedEmail
        );

        if (userRepository.existsByNombreUsuario(request.nombreUsuario())) {
            LOGGER.warn(
                    "Registro rechazado: nombre de usuario duplicado. nombreUsuario={}",
                    request.nombreUsuario()
            );
            throw new UserAlreadyExistsException("El nombre de usuario ya existe");
        }
        LOGGER.debug("Nombre de usuario disponible. nombreUsuario={}", request.nombreUsuario());

        if (userRepository.existsByCorreoElectronico(request.correoElectronico())) {
            LOGGER.warn("Registro rechazado: correo electronico duplicado. correo={}", maskedEmail);
            throw new UserAlreadyExistsException("El correo electrónico ya existe");
        }
        LOGGER.debug("Correo electronico disponible. correo={}", maskedEmail);

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombreUsuario(request.nombreUsuario());
        entity.setCorreoElectronico(request.correoElectronico());
        entity.setHashContrasena(passwordEncoder.encode(request.password()));
        entity.setRol(UserRoleEnum.PLAYER);
        entity.setVigenteDesde(request.vigenteDesde());
        entity.setVigenteHasta(request.vigenteHasta());

        LOGGER.debug("Persistiendo nuevo usuario. nombreUsuario={}", request.nombreUsuario());
        UsuarioEntity createdUser = userRepository.save(entity);
        LOGGER.info(
                "Usuario creado correctamente. usuarioId={}, nombreUsuario={}",
                createdUser.getId(),
                createdUser.getNombreUsuario()
        );

        LOGGER.debug("Creando estado inicial del usuario. usuarioId={}, estado=PENDING", createdUser.getId());
        usuarioEstadoService.crearEstadoInicial(createdUser);

        try {
            emailService.enviarCorreoBienvenida(createdUser);
        } catch (RuntimeException exception) {
            LOGGER.error(
                    "Error durante el envio del correo de activacion. usuarioId={}, correo={}",
                    createdUser.getId(),
                    maskedEmail,
                    exception
            );
            throw exception;
        }

        LOGGER.info(
                "Registro de usuario completado. usuarioId={}, estado=PENDING, duracionMs={}",
                createdUser.getId(),
                System.currentTimeMillis() - startTime
        );
        return userMapper.toResponse(createdUser);
    }

    @Override
    @Transactional
    public UserResponse validateCredentials(UserValidationRequest request) {
        String maskedEmail = SensitiveDataMasker.maskEmail(request.correoElectronico());
        LOGGER.info("Iniciando validacion de credenciales. actor=ANONYMOUS, correo={}", maskedEmail);
        UsuarioEntity entity = userRepository.findByCorreoElectronico(request.correoElectronico())
                .orElseThrow(() -> {
                    LOGGER.warn("Validacion rechazada: correo no encontrado. correo={}", maskedEmail);
                    return new InvalidUserCredentialsException(INVALID_CREDENTIALS_MESSAGE);
                });

        if (!passwordEncoder.matches(request.password(), entity.getHashContrasena())) {
            LOGGER.warn(
                    "Validacion rechazada: contrasena incorrecta. usuarioObjetivoId={}, correo={}",
                    entity.getId(),
                    maskedEmail
            );
            usuarioEstadoService.incrementarIntentoFallido(entity.getId());
            throw new InvalidUserCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }
        LOGGER.debug("Credenciales basicas validadas. usuarioObjetivoId={}", entity.getId());

        UsuarioEstadoEntity estado = obtenerEstadoParaValidacion(entity);
        validarEstadoCuenta(entity, estado);

        LOGGER.info(
                "Validacion de credenciales completada. usuarioObjetivoId={}, nombreUsuario={}",
                entity.getId(),
                entity.getNombreUsuario()
        );

        // Si el login es correcto, se registra el login del usuario reseteando los intentos fallidos y además, se registra la actividad del usuario
        usuarioEstadoService.registrarLogin(entity.getId());
        usuarioEstadoService.registrarActividad(entity.getId());

        return userMapper.toResponse(entity);
    }

    /**
     * Genera un token temporal de restauracion y envia el correo si la cuenta permite esta operacion.
     *
     * La respuesta publica del controlador es generica; por eso los casos de correo inexistente
     * o estado no permitido se registran sin lanzar excepciones funcionales.
     *
     * @param request correo de la cuenta que solicita la restauracion.
     */
    @Override
    @Transactional
    public void solicitarRestauracionContrasena(SolicitudRestauracionContrasenaRequest request) {
        String correoNormalizado = normalizarCorreo(request.correoElectronico());
        String maskedEmail = SensitiveDataMasker.maskEmail(correoNormalizado);
        LOGGER.info("Solicitud de restauracion de contrasena recibida. actor=ANONYMOUS, correo={}", maskedEmail);

        UsuarioEntity usuario = userRepository.findByCorreoElectronicoIgnoreCase(correoNormalizado)
                .orElse(null);
        if (usuario == null) {
            LOGGER.debug("Solicitud de restauracion ignorada: correo no registrado. correo={}", maskedEmail);
            return;
        }

        UsuarioEstadoEntity estado = usuarioEstadoRepository.findByUsuarioId(usuario.getId())
                .orElse(null);
        if (estado == null) {
            LOGGER.warn("Solicitud de restauracion ignorada: usuario sin estado. usuarioObjetivoId={}", usuario.getId());
            return;
        }

        if (!permiteRestauracionContrasena(estado)) {
            LOGGER.info(
                    "Solicitud de restauracion ignorada: estado no permitido. usuarioObjetivoId={}, estadoCuenta={}",
                    usuario.getId(),
                    estado.getAccountStatus()
            );
            return;
        }

        Instant now = Instant.now();
        long expirationMinutes = hermandadUserProperties.getPasswordReset().getTokenExpirationMinutes();
        String token = UUID.randomUUID().toString();
        Instant expiracion = now.plus(expirationMinutes, ChronoUnit.MINUTES);
        estado.setTokenRestauracionContrasena(token);
        estado.setExpiracionTokenRestauracionContrasena(expiracion);
        usuarioEstadoRepository.saveAndFlush(estado);

        String enlaceRestauracion = construirEnlaceRestauracion(token);
        String tiempoExpiracion = expirationMinutes + " minutos";
        emailService.enviarCorreoRestauracionContrasena(usuario, enlaceRestauracion, tiempoExpiracion);
        LOGGER.info("Solicitud de restauracion procesada correctamente. usuarioObjetivoId={}", usuario.getId());
    }

    /**
     * Valida un token de restauracion vigente y actualiza la contrasena del usuario asociado.
     *
     * El token se consume en la misma transaccion que actualiza la contrasena para impedir su reutilizacion.
     *
     * @param request token y nueva contrasena.
     */
    @Override
    @Transactional
    public void confirmarRestauracionContrasena(ConfirmacionRestauracionContrasenaRequest request) {
        LOGGER.info("Confirmacion de restauracion de contrasena recibida. actor=ANONYMOUS");
        validarConfirmacionContrasena(request);

        UsuarioEstadoEntity estado = usuarioEstadoRepository.findByTokenRestauracionContrasena(request.token())
                .orElseThrow(() -> {
                    LOGGER.warn("Restauracion rechazada: token no encontrado");
                    return new IllegalArgumentException(PASSWORD_RESET_INVALID_LINK);
                });

        Instant expiracion = estado.getExpiracionTokenRestauracionContrasena();
        Instant now = Instant.now();
        if (expiracion == null) {
            LOGGER.warn("Restauracion rechazada: token sin expiracion. estadoId={}", estado.getId());
            throw new IllegalArgumentException(PASSWORD_RESET_INVALID_LINK);
        }
        if (!expiracion.isAfter(now)) {
            LOGGER.warn("Restauracion rechazada: token expirado. estadoId={}, expiracion={}", estado.getId(), expiracion);
            throw new IllegalArgumentException(PASSWORD_RESET_EXPIRED_LINK);
        }

        UsuarioEntity usuario = estado.getUsuario();
        if (usuario == null) {
            LOGGER.warn("Restauracion rechazada: estado sin usuario asociado. estadoId={}", estado.getId());
            throw new IllegalArgumentException(PASSWORD_RESET_INVALID_LINK);
        }
        if (!permiteRestauracionContrasena(estado)) {
            LOGGER.warn(
                    "Restauracion rechazada: estado no permitido. usuarioObjetivoId={}, estadoCuenta={}",
                    usuario.getId(),
                    estado.getAccountStatus()
            );
            throw new IllegalStateException(PASSWORD_RESET_FORBIDDEN_ACCOUNT);
        }

        usuario.setHashContrasena(passwordEncoder.encode(request.nuevaContrasena()));
        estado.setPasswordChangedAt(now);
        estado.setAccountStatus(AccountStatusEnum.ACTIVE);
        estado.setTokenRestauracionContrasena(null);
        estado.setExpiracionTokenRestauracionContrasena(null);
        userRepository.save(usuario);
        usuarioEstadoRepository.save(estado);
        LOGGER.info("Restauracion de contrasena completada. usuarioObjetivoId={}", usuario.getId());
    }

    private UsuarioEstadoEntity obtenerEstadoParaValidacion(UsuarioEntity usuario) {
        try {
            return usuarioEstadoService.buscarPorUsuarioId(usuario.getId());
        } catch (IllegalArgumentException exception) {
            LOGGER.warn(
                    "Validacion rechazada: estado de usuario no encontrado. usuarioObjetivoId={}",
                    usuario.getId()
            );
            throw new InvalidUserCredentialsException(USUARIO_NO_ACTIVADO);
        }
    }

    private String normalizarCorreo(String correoElectronico) {
        return correoElectronico.trim().toLowerCase(Locale.ROOT);
    }

    private boolean permiteRestauracionContrasena(UsuarioEstadoEntity estado) {
        return estado.getAccountStatus() != AccountStatusEnum.LOCKED
                && estado.getAccountStatus() != AccountStatusEnum.DELETED;
    }

    private String construirEnlaceRestauracion(String token) {
        return UriComponentsBuilder.fromUriString(enlaceRestauracionContrasena)
                .queryParam("token", token)
                .build()
                .toUriString();
    }

    private void validarConfirmacionContrasena(ConfirmacionRestauracionContrasenaRequest request) {
        if (!request.nuevaContrasena().equals(request.confirmacionContrasena())) {
            throw new IllegalArgumentException(PASSWORD_RESET_PASSWORD_MISMATCH);
        }
        if (!cumplePoliticaContrasena(request.nuevaContrasena())) {
            throw new IllegalArgumentException(PASSWORD_RESET_INVALID_PASSWORD);
        }
    }

    private boolean cumplePoliticaContrasena(String password) {
        HermandadUserProperties.Password properties = hermandadUserProperties.getPassword();
        if (password.length() < properties.getMinLength()) {
            return false;
        }
        if (properties.isRequireUppercase() && password.chars().noneMatch(Character::isUpperCase)) {
            return false;
        }
        if (properties.isRequireNumber() && password.chars().noneMatch(Character::isDigit)) {
            return false;
        }
        return !properties.isRequireSpecialCharacter()
                || password.chars().anyMatch(character -> !Character.isLetterOrDigit(character));
    }

    private void validarEstadoCuenta(UsuarioEntity usuario, UsuarioEstadoEntity estado) {
        AccountStatusEnum accountStatus = estado.getAccountStatus();
        if (accountStatus == AccountStatusEnum.ACTIVE) {
            return;
        }

        if (accountStatus == AccountStatusEnum.PENDING) {
            LOGGER.warn(
                    "Validacion rechazada: usuario no activado. usuarioObjetivoId={}, estadoCuenta={}",
                    usuario.getId(),
                    accountStatus
            );
            throw new InvalidUserCredentialsException(USUARIO_NO_ACTIVADO);
        }

        if (accountStatus == AccountStatusEnum.LOCKED) {
            LOGGER.warn(
                    "Validacion rechazada: usuario bloqueado. usuarioObjetivoId={}, estadoCuenta={}, lockedUntil={}",
                    usuario.getId(),
                    accountStatus,
                    estado.getLockedUntil()
            );
            throw new InvalidUserCredentialsException(USUARIO_BLOQUEADO);
        }

        LOGGER.warn(
                "Validacion rechazada: usuario inactivo. usuarioObjetivoId={}, estadoCuenta={}",
                usuario.getId(),
                accountStatus
        );
        throw new InvalidUserCredentialsException(USUARIO_INACTIVO);
    }
}
