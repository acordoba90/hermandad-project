package com.hermandadproject.gestioninventario.repository;

import com.hermandadproject.gestioninventario.model.entity.ElementoInventarioEntity;
import com.hermandadproject.gestioninventario.model.enums.InventoryItemTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryItemRepository extends JpaRepository<ElementoInventarioEntity, UUID> {
    List<ElementoInventarioEntity> findByIdHermandadAndActivoTrue(UUID idHermandad);

    List<ElementoInventarioEntity> findByIdHermandadAndTipoElementoAndActivoTrue(UUID idHermandad, InventoryItemTypeEnum tipoElemento);

    Optional<ElementoInventarioEntity> findByIdHermandadAndTipoElementoAndCodigoElementoAndActivoTrue(
            UUID idHermandad,
            InventoryItemTypeEnum tipoElemento,
            String codigoElemento
    );

    Optional<ElementoInventarioEntity> findByIdHermandadAndTipoElementoAndCodigoElemento(
            UUID idHermandad,
            InventoryItemTypeEnum tipoElemento,
            String codigoElemento
    );

    boolean existsByIdHermandadAndTipoElementoAndCodigoElementoAndActivoTrue(
            UUID idHermandad,
            InventoryItemTypeEnum tipoElemento,
            String codigoElemento
    );
}
