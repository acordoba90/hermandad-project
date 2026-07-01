package com.hermandadproject.gestionrecompensas.repository;

import com.hermandadproject.gestionrecompensas.model.entity.AperturaSobreRecompensaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RewardPackOpeningRepository extends JpaRepository<AperturaSobreRecompensaEntity, UUID> {
    List<AperturaSobreRecompensaEntity> findByIdHermandad(UUID idHermandad);
}
