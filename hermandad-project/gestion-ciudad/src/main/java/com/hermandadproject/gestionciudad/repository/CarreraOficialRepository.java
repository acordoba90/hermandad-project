package com.hermandadproject.gestionciudad.repository;

import com.hermandadproject.gestionciudad.model.entity.CarreraOficialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarreraOficialRepository extends JpaRepository<CarreraOficialEntity, UUID> {
    Optional<CarreraOficialEntity> findByCiudadIdAndActivaTrue(UUID ciudadId);

    Optional<CarreraOficialEntity> findByMapaCiudadIdAndActivaTrue(UUID mapaCiudadId);
}
