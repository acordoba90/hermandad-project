package com.hermandadproject.gestionpasos.repository;

import com.hermandadproject.gestionpasos.model.entity.FiguraColocadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlacedFigureRepository extends JpaRepository<FiguraColocadaEntity, UUID> {
    List<FiguraColocadaEntity> findByIdHermandadAndPlantillaPasoId(UUID idHermandad, UUID idPlantillaPaso);

    boolean existsByIdHermandadAndPlantillaPasoIdAndHuecoPasoId(UUID idHermandad, UUID idPlantillaPaso, UUID idHuecoPaso);
}
