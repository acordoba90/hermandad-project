package com.hermandadproject.gestionrecompensas.repository;

import com.hermandadproject.gestionrecompensas.model.entity.SobreRecompensaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RewardPackRepository extends JpaRepository<SobreRecompensaEntity, UUID> {
    Optional<SobreRecompensaEntity> findByCodigo(String codigo);

    List<SobreRecompensaEntity> findByActivoTrue();

    boolean existsByCodigo(String codigo);
}
