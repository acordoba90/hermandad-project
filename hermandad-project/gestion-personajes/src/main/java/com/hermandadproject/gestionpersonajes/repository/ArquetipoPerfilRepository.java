package com.hermandadproject.gestionpersonajes.repository;

import com.hermandadproject.gestionpersonajes.model.entity.ArquetipoPerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de acceso a arquetipos de perfil.
 */
public interface ArquetipoPerfilRepository extends JpaRepository<ArquetipoPerfilEntity, UUID> {
    Optional<ArquetipoPerfilEntity> findByCodigo(String codigo);

    Optional<ArquetipoPerfilEntity> findByIdAndActivoTrue(UUID id);

    boolean existsByCodigo(String codigo);

    List<ArquetipoPerfilEntity> findAllByActivoTrueOrderByNombreAsc();
}
