package com.hermandadproject.gestionhermandades.repository;

import com.hermandadproject.gestionhermandades.model.entity.HermandadEntity;
import com.hermandadproject.gestionhermandades.model.enums.EstadoHermandad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HermandadRepository extends JpaRepository<HermandadEntity, UUID> {
    List<HermandadEntity> findByIdUsuario(UUID idUsuario);

    boolean existsByIdUsuarioAndNombre(UUID idUsuario, String nombre);

    List<HermandadEntity> findByEstado(EstadoHermandad estado);

    List<HermandadEntity> findAllByOrderByPrestigioDesc();

    List<HermandadEntity> findAllByOrderByPopularidadDesc();

    List<HermandadEntity> findAllByOrderByDevocionDesc();

    List<HermandadEntity> findAllByOrderBySolemnidadDesc();
}

