package com.hermandadproject.gestionusuarios.repository;

import com.hermandadproject.gestionusuarios.model.entity.PerfilUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<PerfilUsuarioEntity, UUID> {
    Optional<PerfilUsuarioEntity> findByUsuarioId(UUID idUsuario);

    boolean existsByUsuarioId(UUID idUsuario);

    boolean existsByAlias(String alias);
}
