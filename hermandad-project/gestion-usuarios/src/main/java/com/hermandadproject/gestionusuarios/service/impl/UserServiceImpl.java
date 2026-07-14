package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.exception.InvalidUserCredentialsException;
import com.hermandadproject.gestionusuarios.exception.UserAlreadyExistsException;
import com.hermandadproject.gestionusuarios.exception.UserNotFoundException;
import com.hermandadproject.gestionusuarios.logging.SensitiveDataMasker;
import com.hermandadproject.gestionusuarios.mapper.UserMapper;
import com.hermandadproject.gestionusuarios.model.dto.UserCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserValidationRequest;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEstadoEntity;
import com.hermandadproject.gestionusuarios.model.enums.AccountStatusEnum;
import com.hermandadproject.gestionusuarios.model.enums.UserRoleEnum;
import com.hermandadproject.gestionusuarios.repository.UserRepository;
import com.hermandadproject.gestionusuarios.service.EmailService;
import com.hermandadproject.gestionusuarios.service.UserService;
import com.hermandadproject.gestionusuarios.service.UsuarioEstadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String INVALID_CREDENTIALS_MESSAGE = "El correo o la contraseña introducida no son correctos";
    private static final String USUARIO_NO_ACTIVADO = "El usuario no se encuentra activado, por favor, revise su correo electrónico";
    private static final String USUARIO_INACTIVO = "El usuario ha sido dado de baja por inactividad";
    private static final String USUARIO_BLOQUEADO = "La cuenta esta bloqueada temporalmente";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioEstadoService usuarioEstadoService;
    private final EmailService emailService;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            UsuarioEstadoService usuarioEstadoService,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.usuarioEstadoService = usuarioEstadoService;
        this.emailService = emailService;
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
