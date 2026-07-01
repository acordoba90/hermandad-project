package com.hermandadproject.gestionpersonajes.repository;

import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColectivoRepository extends JpaRepository<ColectivoEntity, UUID> {
    Optional<ColectivoEntity> findByCodigo(String codigo);

    List<ColectivoEntity> findByActivoTrue();

    boolean existsByCodigo(String codigo);
}
