package com.hermandadproject.gestionciudad.repository;

import com.hermandadproject.gestionciudad.model.entity.CiudadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CiudadRepository extends JpaRepository<CiudadEntity, UUID> {
    Optional<CiudadEntity> findByCodigo(String codigo);

    List<CiudadEntity> findByActivaTrue();

    boolean existsByCodigo(String codigo);
}
