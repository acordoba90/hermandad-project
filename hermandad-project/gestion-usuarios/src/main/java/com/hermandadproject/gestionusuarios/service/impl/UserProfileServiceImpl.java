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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

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
        UsuarioEntity usuario = userRepository.findById(request.idUsuario())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (userProfileRepository.existsByUsuarioId(request.idUsuario())) {
            throw new UserProfileAlreadyExistsException("El usuario ya tiene un perfil");
        }
        if (userProfileRepository.existsByAlias(request.alias())) {
            throw new UserProfileAlreadyExistsException("El alias ya existe");
        }

        PerfilUsuarioEntity entity = userProfileMapper.toEntity(request, usuario);
        entity.setNivel(1);
        entity.setExperiencia(0);

        PerfilUsuarioEntity saved = userProfileRepository.save(entity);
        return userProfileMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse findById(UUID id) {
        PerfilUsuarioEntity entity = findEntityById(id);
        return userProfileMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse findByUsuarioId(UUID idUsuario) {
        if (!userRepository.existsById(idUsuario)) {
            throw new UserNotFoundException("Usuario no encontrado");
        }

        PerfilUsuarioEntity entity = userProfileRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new UserProfileNotFoundException("Perfil de usuario no encontrado"));
        return userProfileMapper.toResponse(entity);
    }

    @Override
    public UserProfileResponse update(UUID id, UserProfileUpdateRequest request) {
        PerfilUsuarioEntity entity = findEntityById(id);

        if (!entity.getAlias().equals(request.alias()) && userProfileRepository.existsByAlias(request.alias())) {
            throw new UserProfileAlreadyExistsException("El alias ya existe");
        }

        userProfileMapper.updateEntity(entity, request);
        PerfilUsuarioEntity saved = userProfileRepository.save(entity);
        return userProfileMapper.toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        PerfilUsuarioEntity entity = findEntityById(id);
        userProfileRepository.delete(entity);
    }

    private PerfilUsuarioEntity findEntityById(UUID id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException("Perfil de usuario no encontrado"));
    }
}
