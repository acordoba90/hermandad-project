package com.hermandadproject.gestionrecompensas.repository;

import com.hermandadproject.gestionrecompensas.model.entity.RecompensaSobreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RewardPackRewardRepository extends JpaRepository<RecompensaSobreEntity, UUID> {
    List<RecompensaSobreEntity> findByAperturaId(UUID idApertura);
}
