package com.hermandadproject.gestionusuarios.service.impl;

import com.hermandadproject.gestionusuarios.exception.UserNotFoundException;
import com.hermandadproject.gestionusuarios.exception.UserProfileAlreadyExistsException;
import com.hermandadproject.gestionusuarios.exception.UserProfileNotFoundException;
import com.hermandadproject.gestionusuarios.mapper.UserProfileMapper;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileCreateRequest;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileResponse;
import com.hermandadproject.gestionusuarios.model.dto.UserProfileUpdateRequest;
import com.hermandadproject.gestionusuarios.model.entity.UsuarioEntity;
import com.hermandadproject.gestionusuarios.model.entity.PerfilUsuarioEntity;
import com.hermandadproject.gestionusuarios.repository.UserProfileRepository;
import com.hermandadproject.gestionusuarios.repository.UserRepository;
import com.hermandadproject.gestionusuarios.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileServiceImpl(
            UserProfileRepository userProfileRepository,
            UserRepository userRepository,
            UserProfileMapper userProfileMapper
    ) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public UserProfileResponse create(UserProfileCreateRequest request) {
        LOGGER.info(
                "Iniciando creacion de perfil de usuario. usuarioObjetivoId={}, alias={}",
                request.idUsuario(),
                request.alias()
        );
        UsuarioEntity usuario = userRepository.findById(request.idUsuario())
                .orElseThrow(() -> {
                    LOGGER.warn("Creacion de perfil rechazada: usuario no encontrado. usuarioObjetivoId={}", request.idUsuario());
                    return new UserNotFoundException("Usuario no encontrado");
                });
        LOGGER.debug("Usuario localizado para crear perfil. usuarioObjetivoId={}", usuario.getId());

        if (userProfileRepository.existsByUsuarioId(request.idUsuario())) {
            LOGGER.warn("Creacion de perfil rechazada: usuario ya tiene perfil. usuarioObjetivoId={}", request.idUsuario());
            throw new UserProfileAlreadyExistsException("El usuario ya tiene un perfil");
        }
        if (userProfileRepository.existsByAlias(request.alias())) {
            LOGGER.warn("Creacion de perfil rechazada: alias duplicado. alias={}", request.alias());
            throw new UserProfileAlreadyExistsException("El alias ya existe");
        }
        LOGGER.debug("Validaciones de perfil superadas. usuarioObjetivoId={}, alias={}", request.idUsuario(), request.alias());

        PerfilUsuarioEntity entity = userProfileMapper.toEntity(request, usuario);
        entity.setNivel(1);
        entity.setExperiencia(0);

        LOGGER.debug("Persistiendo perfil de usuario. usuarioObjetivoId={}, alias={}", request.idUsuario(), request.alias());
        PerfilUsuarioEntity saved = userProfileRepository.save(entity);
        LOGGER.info(
                "Perfil de usuario creado correctamente. perfilId={}, usuarioObjetivoId={}, alias={}",
                saved.getId(),
                request.idUsuario(),
                saved.getAlias()
        );
        return userProfileMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse findById(UUID id) {
        LOGGER.debug("Consultando perfil por identificador. perfilId={}", id);
        PerfilUsuarioEntity entity = findEntityById(id);
        LOGGER.debug("Perfil localizado. perfilId={}, usuarioObjetivoId={}", id, entity.getUsuario().getId());
        return userProfileMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse findByUsuarioId(UUID idUsuario) {
        LOGGER.debug("Consultando perfil por usuario. usuarioObjetivoId={}", idUsuario);
        if (!userRepository.existsById(idUsuario)) {
            LOGGER.warn("Consulta de perfil rechazada: usuario no encontrado. usuarioObjetivoId={}", idUsuario);
            throw new UserNotFoundException("Usuario no encontrado");
        }

        PerfilUsuarioEntity entity = userProfileRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> {
                    LOGGER.warn("Perfil de usuario no encontrado. usuarioObjetivoId={}", idUsuario);
                    return new UserProfileNotFoundException("Perfil de usuario no encontrado");
                });
        LOGGER.debug("Perfil localizado por usuario. perfilId={}, usuarioObjetivoId={}", entity.getId(), idUsuario);
        return userProfileMapper.toResponse(entity);
    }

    @Override
    public UserProfileResponse update(UUID id, UserProfileUpdateRequest request) {
        LOGGER.info("Iniciando actualizacion de perfil. perfilId={}, alias={}", id, request.alias());
        PerfilUsuarioEntity entity = findEntityById(id);

        if (!entity.getAlias().equals(request.alias()) && userProfileRepository.existsByAlias(request.alias())) {
            LOGGER.warn("Actualizacion de perfil rechazada: alias duplicado. perfilId={}, alias={}", id, request.alias());
            throw new UserProfileAlreadyExistsException("El alias ya existe");
        }

        LOGGER.debug("Persistiendo actualizacion de perfil. perfilId={}, usuarioObjetivoId={}", id, entity.getUsuario().getId());
        userProfileMapper.updateEntity(entity, request);
        PerfilUsuarioEntity saved = userProfileRepository.save(entity);
        LOGGER.info(
                "Perfil actualizado correctamente. perfilId={}, usuarioObjetivoId={}",
                saved.getId(),
                saved.getUsuario().getId()
        );
        return userProfileMapper.toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        LOGGER.info("Iniciando eliminacion de perfil. perfilId={}", id);
        PerfilUsuarioEntity entity = findEntityById(id);
        userProfileRepository.delete(entity);
        LOGGER.info("Perfil eliminado correctamente. perfilId={}, usuarioObjetivoId={}", id, entity.getUsuario().getId());
    }

    private PerfilUsuarioEntity findEntityById(UUID id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Perfil de usuario no encontrado. perfilId={}", id);
                    return new UserProfileNotFoundException("Perfil de usuario no encontrado");
                });
    }
}
