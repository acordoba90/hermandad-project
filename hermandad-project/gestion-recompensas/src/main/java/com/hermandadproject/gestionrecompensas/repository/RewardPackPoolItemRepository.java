package com.hermandadproject.gestionrecompensas.repository;

import com.hermandadproject.gestionrecompensas.model.entity.ElementoPoolSobreRecompensaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RewardPackPoolItemRepository extends JpaRepository<ElementoPoolSobreRecompensaEntity, UUID> {
    List<ElementoPoolSobreRecompensaEntity> findBySobreRecompensaIdAndActivoTrue(UUID idSobreRecompensa);
}
