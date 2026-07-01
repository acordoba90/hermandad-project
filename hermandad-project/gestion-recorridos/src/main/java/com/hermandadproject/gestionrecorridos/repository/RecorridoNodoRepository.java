package com.hermandadproject.gestionrecorridos.repository;

import com.hermandadproject.gestionrecorridos.model.entity.RecorridoNodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecorridoNodoRepository extends JpaRepository<RecorridoNodoEntity, UUID> {
    List<RecorridoNodoEntity> findByRecorridoIdOrderByOrdenAsc(UUID recorridoId);

    void deleteByRecorridoId(UUID recorridoId);
}
