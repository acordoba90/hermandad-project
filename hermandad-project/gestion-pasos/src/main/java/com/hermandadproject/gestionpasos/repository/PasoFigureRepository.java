package com.hermandadproject.gestionpasos.repository;

import com.hermandadproject.gestionpasos.model.entity.FiguraPasoEntity;
import com.hermandadproject.gestionpasos.model.enums.FigureTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PasoFigureRepository extends JpaRepository<FiguraPasoEntity, UUID> {
    Optional<FiguraPasoEntity> findByCodigo(String codigo);

    List<FiguraPasoEntity> findByActivoTrue();

    List<FiguraPasoEntity> findByTipoAndActivoTrue(FigureTypeEnum tipo);

    boolean existsByCodigo(String codigo);
}
