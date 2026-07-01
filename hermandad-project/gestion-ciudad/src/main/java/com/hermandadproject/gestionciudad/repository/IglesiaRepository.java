package com.hermandadproject.gestionciudad.repository;

import com.hermandadproject.gestionciudad.model.entity.IglesiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IglesiaRepository extends JpaRepository<IglesiaEntity, UUID> {
    Optional<IglesiaEntity> findByCodigo(String codigo);

    List<IglesiaEntity> findByCiudadIdAndActivaTrue(UUID ciudadId);

    List<IglesiaEntity> findByCiudadIdAndDisponibleComoSedeTrueAndActivaTrue(UUID ciudadId);

    List<IglesiaEntity> findByCiudadIdAndConstruibleTrueAndActivaTrue(UUID ciudadId);

    boolean existsByCodigo(String codigo);
}
