package com.hermandadproject.gestionpersonajes.repository;

import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonajeRepository extends JpaRepository<PersonajeEntity, UUID> {
    Optional<PersonajeEntity> findByCodigo(String codigo);

    List<PersonajeEntity> findByActivoTrue();

    List<PersonajeEntity> findByColectivoIdAndActivoTrue(UUID colectivoId);

    List<PersonajeEntity> findByColectivoCodigoAndActivoTrue(String colectivoCode);

    boolean existsByCodigo(String codigo);
}
