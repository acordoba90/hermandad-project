package com.hermandadproject.gestionpersonajes.repository;

import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonajeRepository extends JpaRepository<PersonajeEntity, UUID> {
    Optional<PersonajeEntity> findByCodigo(String codigo);

    List<PersonajeEntity> findByActivoTrue();

    List<PersonajeEntity> findByColectivoIdAndActivoTrue(UUID colectivoId);

    List<PersonajeEntity> findByColectivoCodigoAndActivoTrue(String colectivoCode);

    /**
     * Recupera los personajes predefinidos de un rol y colectivo junto con los datos necesarios
     * para presentar su perfil, evitando cargas diferidas por cada resultado.
     *
     * @param colectivoCodigo codigo funcional del colectivo
     * @param rolCodigo codigo funcional del rol
     * @return personajes activos y no personalizables con sus relaciones cargadas
     */
    @Query("""
            select personaje
            from PersonajeEntity personaje
            join fetch personaje.colectivo colectivo
            join fetch personaje.rolPersonaje rol
            left join fetch personaje.perfil perfil
            left join fetch perfil.arquetipoOrigen arquetipo
            where personaje.activo = true
              and personaje.personalizado = false
              and colectivo.activo = true
              and colectivo.codigo = :colectivoCodigo
              and rol.activo = true
              and rol.codigo = :rolCodigo
            order by personaje.nombre, personaje.apellidos
            """)
    List<PersonajeEntity> findPredefinidosByColectivoAndRol(
            @Param("colectivoCodigo") String colectivoCodigo,
            @Param("rolCodigo") String rolCodigo
    );

    boolean existsByCodigo(String codigo);
}
