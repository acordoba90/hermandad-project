package com.hermandadproject.gestionciudad.repository;

import com.hermandadproject.gestionciudad.model.entity.ConexionCiudadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConexionCiudadRepository extends JpaRepository<ConexionCiudadEntity, UUID> {
    List<ConexionCiudadEntity> findByMapaCiudadIdAndActivaTrue(UUID mapaCiudadId);

    List<ConexionCiudadEntity> findByNodoOrigenIdOrNodoDestinoId(UUID nodoOrigenId, UUID nodoDestinoId);

    boolean existsByMapaCiudadIdAndNodoOrigenIdAndNodoDestinoId(UUID mapaCiudadId, UUID nodoOrigenId, UUID nodoDestinoId);
}
