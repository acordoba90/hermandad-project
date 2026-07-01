package com.hermandadproject.gestionrecorridos.repository;

import com.hermandadproject.gestionrecorridos.model.entity.RecorridoEntity;
import com.hermandadproject.gestionrecorridos.model.enums.EstadoRecorridoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecorridoRepository extends JpaRepository<RecorridoEntity, UUID> {
    List<RecorridoEntity> findByIdHermandadAndActivoTrue(UUID idHermandad);

    List<RecorridoEntity> findByIdHermandadAndEstadoAndActivoTrue(UUID idHermandad, EstadoRecorridoEnum estado);

    Optional<RecorridoEntity> findByIdHermandadAndActivoTrueAndEstado(UUID idHermandad, EstadoRecorridoEnum estado);
}
