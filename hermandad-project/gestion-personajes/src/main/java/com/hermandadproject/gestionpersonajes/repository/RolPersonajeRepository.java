package com.hermandadproject.gestionpersonajes.repository;

import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de acceso a datos para el catalogo de roles de personaje.
 */
public interface RolPersonajeRepository extends JpaRepository<RolPersonajeEntity, UUID> {
    Optional<RolPersonajeEntity> findByIdAndActivoTrue(UUID id);

    List<RolPersonajeEntity> findAllByActivoTrueOrderByNombreAsc();

    List<RolPersonajeEntity> findAllByColectivoIdAndActivoTrueOrderByNombreAsc(UUID colectivoId);
}
