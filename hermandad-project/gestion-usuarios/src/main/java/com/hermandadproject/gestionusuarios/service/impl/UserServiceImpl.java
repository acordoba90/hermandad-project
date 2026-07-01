package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.exception.UserAlreadyExistsException;
import com.hermandadproject.gestionusuarios.exception.InvalidUserCredentialsException;
import com.hermandadproject.gestionusuarios.exception.UserNotFoundException;
import com.hermandadproject.gestionusuarios.mapper.UserMapper;
import com.hermandadproject.gestionusuarios.model.dto.UserCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserValidationRequest;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.enums.UserRoleEnum;
import com.hermandadproject.gestionusuarios.repository.UserRepository;
import com.hermandadproject.gestionusuarios.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final String INVALID_CREDENTIALS_MESSAGE = "El correo o la contraseña introducida no son correctos";
    private static final String USUARIO_NO_ACTIVADO = "El usuario no se encuentra activado, por favor, revise su correo electrónico";
    private static final String USUARIO_INACTIVO = "El usuario ha sido dado de baja por inactividad";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Override
    public UserResponse getById(UUID id) {
        UsuarioEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        return userMapper.toResponse(entity);
    }

    @Override
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByNombreUsuario(request.nombreUsuario())) {
            throw new UserAlreadyExistsException("El nombre de usuario ya existe");
        }
        if (userRepository.existsByCorreoElectronico(request.correoElectronico())) {
            throw new UserAlreadyExistsException("El correo electrónico ya existe");
        }

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombreUsuario(request.nombreUsuario());
        entity.setCorreoElectronico(request.correoElectronico());
        entity.setHashContrasena(passwordEncoder.encode(request.password()));
        entity.setRol(UserRoleEnum.PLAYER);
        entity.setVigenteDesde(request.vigenteDesde());
        entity.setVigenteHasta(request.vigenteHasta());

        UsuarioEntity saved = userRepository.save(entity);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse validateCredentials(UserValidationRequest request) {
        UsuarioEntity entity = userRepository.findByCorreoElectronico(request.correoElectronico())
                .orElseThrow(() -> new InvalidUserCredentialsException(INVALID_CREDENTIALS_MESSAGE));

        if (!passwordEncoder.matches(request.password(), entity.getHashContrasena())) {
            throw new InvalidUserCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }

        if (entity.getVigenteDesde() == null || entity.getVigenteDesde().isAfter(Instant.now())) {
            throw new InvalidUserCredentialsException(USUARIO_NO_ACTIVADO);
        }

        if (entity.getVigenteHasta() != null && entity.getVigenteHasta().isBefore(Instant.now())) {
            throw new InvalidUserCredentialsException(USUARIO_INACTIVO);
        }

        return userMapper.toResponse(entity);
    }
}
