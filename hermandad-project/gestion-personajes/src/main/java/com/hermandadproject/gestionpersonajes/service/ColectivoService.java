package com.hermandadproject.gestionpersonajes.service;

import com.hermandadproject.gestionpersonajes.model.dto.ColectivoCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoResponse;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoUpdateRequest;

import java.util.List;
import java.util.UUID;

/**
 * Servicio de aplicacion para gestionar colectivos de personajes.
 */
public interface ColectivoService {
    /**
     * Crea un colectivo validando la unicidad de su codigo tecnico.
     */
    ColectivoResponse create(ColectivoCreateRequest request);

    /**
     * Busca un colectivo por su identificador.
     */
    ColectivoResponse findById(UUID id);

    /**
     * Busca un colectivo por su codigo tecnico.
     */
    ColectivoResponse findByCodigo(String codigo);

    /**
     * Devuelve los colectivos activos disponibles para el juego.
     */
    List<ColectivoResponse> findAllActive();

    /**
     * Actualiza los datos modificables de un colectivo.
     */
    ColectivoResponse update(UUID id, ColectivoUpdateRequest request);

    /**
     * Desactiva un colectivo sin eliminarlo fisicamente.
     */
    void delete(UUID id);
}
