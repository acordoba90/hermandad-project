package com.hermandadproject.gestionhermandades.repository;

import com.hermandadproject.gestionhermandades.model.entity.EconomiaHermandadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EconomiaHermandadRepository extends JpaRepository<EconomiaHermandadEntity, UUID> {
    Optional<EconomiaHermandadEntity> findByHermandadId(UUID uuidHermandad);
}

