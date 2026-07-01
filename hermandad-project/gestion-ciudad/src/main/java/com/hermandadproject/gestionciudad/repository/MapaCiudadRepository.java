package com.hermandadproject.gestionciudad.repository;

import com.hermandadproject.gestionciudad.model.entity.MapaCiudadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MapaCiudadRepository extends JpaRepository<MapaCiudadEntity, UUID> {
    Optional<MapaCiudadEntity> findByCodigo(String codigo);

    List<MapaCiudadEntity> findByCiudadIdAndActivoTrue(UUID ciudadId);

    boolean existsByCodigo(String codigo);
}
