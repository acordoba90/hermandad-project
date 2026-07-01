package com.hermandadproject.gestionhermandades.repository;

import com.hermandadproject.gestionhermandades.model.entity.MovimientoEconomicoHermandadEntity;
import com.hermandadproject.gestionhermandades.model.enums.TipoMovimientoEconomico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimientoEconomicoHermandadRepository extends JpaRepository<MovimientoEconomicoHermandadEntity, UUID> {
    List<MovimientoEconomicoHermandadEntity> findByHermandadIdOrderByFechaMovimientoDesc(UUID uuidHermandad);

    List<MovimientoEconomicoHermandadEntity> findByHermandadIdAndTipoMovimientoOrderByFechaMovimientoDesc(
            UUID uuidHermandad,
            TipoMovimientoEconomico tipoMovimiento
    );
}

