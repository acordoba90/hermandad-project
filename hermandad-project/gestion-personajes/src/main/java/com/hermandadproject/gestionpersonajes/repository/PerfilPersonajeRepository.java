package com.hermandadproject.gestionpersonajes.repository;

import com.hermandadproject.gestionpersonajes.model.entity.PerfilPersonajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de acceso a perfiles evolutivos de personajes.
 */
public interface PerfilPersonajeRepository extends JpaRepository<PerfilPersonajeEntity, UUID> {
    Optional<PerfilPersonajeEntity> findByPersonajeId(UUID personajeId);

    boolean existsByPersonajeId(UUID personajeId);
}
