package com.hermandadproject.gestionciudad.repository;

import com.hermandadproject.gestionciudad.model.entity.NodoCiudadEntity;
import com.hermandadproject.gestionciudad.model.enums.TipoNodoCiudadEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NodoCiudadRepository extends JpaRepository<NodoCiudadEntity, UUID> {
    Optional<NodoCiudadEntity> findByMapaCiudadIdAndCodigo(UUID mapaCiudadId, String codigo);

    List<NodoCiudadEntity> findByMapaCiudadIdAndActivoTrue(UUID mapaCiudadId);

    List<NodoCiudadEntity> findByMapaCiudadIdAndTipoAndActivoTrue(UUID mapaCiudadId, TipoNodoCiudadEnum tipo);

    boolean existsByMapaCiudadIdAndCodigo(UUID mapaCiudadId, String codigo);
}
