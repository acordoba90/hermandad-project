package com.hermandadproject.gestionhermandades.repository;

import com.hermandadproject.gestionhermandades.model.entity.CarismaHermandadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarismaHermandadRepository extends JpaRepository<CarismaHermandadEntity, UUID> {
    Optional<CarismaHermandadEntity> findByCodigo(String codigo);

    List<CarismaHermandadEntity> findByActivoTrueOrderByOrdenAsc();

    boolean existsByCodigo(String codigo);

    List<CarismaHermandadEntity> findByUuidIn(Collection<UUID> uuids);
}

