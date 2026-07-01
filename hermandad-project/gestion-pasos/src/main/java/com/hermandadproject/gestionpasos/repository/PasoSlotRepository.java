package com.hermandadproject.gestionpasos.repository;

import com.hermandadproject.gestionpasos.model.entity.HuecoPasoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PasoSlotRepository extends JpaRepository<HuecoPasoEntity, UUID> {
    List<HuecoPasoEntity> findByPlantillaPasoIdAndActivoTrue(UUID idPlantillaPaso);

    Optional<HuecoPasoEntity> findByPlantillaPasoIdAndClaveHueco(UUID idPlantillaPaso, String claveHueco);
}
