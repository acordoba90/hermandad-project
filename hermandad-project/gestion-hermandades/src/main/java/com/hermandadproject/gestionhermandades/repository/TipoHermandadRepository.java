package com.hermandadproject.gestionhermandades.repository;

import com.hermandadproject.gestionhermandades.model.entity.TipoHermandadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TipoHermandadRepository extends JpaRepository<TipoHermandadEntity, UUID> {
    Optional<TipoHermandadEntity> findByCodigo(String codigo);

    List<TipoHermandadEntity> findByActivoTrueOrderByOrdenAsc();

    boolean existsByCodigo(String codigo);
}

