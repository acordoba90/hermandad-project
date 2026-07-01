package com.hermandadproject.gestionhermandades.repository;

import com.hermandadproject.gestionhermandades.model.entity.TipoHermandadCaracteristicasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TipoHermandadCaracteristicasRepository extends JpaRepository<TipoHermandadCaracteristicasEntity, UUID> {
    Optional<TipoHermandadCaracteristicasEntity> findByTipoHermandadUuid(UUID tipoHermandadUuid);

    Optional<TipoHermandadCaracteristicasEntity> findByTipoHermandadCodigo(String codigo);
}
