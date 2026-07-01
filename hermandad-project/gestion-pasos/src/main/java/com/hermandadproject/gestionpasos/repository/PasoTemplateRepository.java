package com.hermandadproject.gestionpasos.repository;

import com.hermandadproject.gestionpasos.model.entity.PlantillaPasoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PasoTemplateRepository extends JpaRepository<PlantillaPasoEntity, UUID> {
    Optional<PlantillaPasoEntity> findByCodigo(String codigo);

    List<PlantillaPasoEntity> findByActivoTrue();

    boolean existsByCodigo(String codigo);
}
