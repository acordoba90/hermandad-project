package com.hermandadproject.gestionpersonajes.service;

import com.hermandadproject.gestionpersonajes.model.dto.PersonajeCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface PersonajeService {
    PersonajeResponse create(PersonajeCreateRequest request);

    PersonajeResponse findById(UUID id);

    PersonajeResponse findByCodigo(String codigo);

    List<PersonajeResponse> findAllActive();

    List<PersonajeResponse> findByColectivoId(UUID colectivoId);

    List<PersonajeResponse> findByColectivoCode(String colectivoCode);

    /**
     * Obtiene los Hermanos Mayores predefinidos disponibles para la Junta de Gobierno.
     *
     * @return personajes que cumplen el colectivo, rol y tipo de personalizacion requeridos
     */
    List<PersonajeResponse> findHermanosMayoresPredefinidos();

    PersonajeResponse update(UUID id, PersonajeUpdateRequest request);

    void delete(UUID id);
}
