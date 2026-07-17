package com.hermandadproject.gestionpersonajes.service;

import com.hermandadproject.gestionpersonajes.model.dto.ArquetipoPerfilResponse;

import java.util.List;
import java.util.UUID;

/**
 * Servicio de consulta del catalogo de arquetipos de perfil.
 */
public interface ArquetipoPerfilService {
    /**
     * Devuelve los arquetipos activos ordenados por nombre.
     *
     * @return arquetipos activos
     */
    List<ArquetipoPerfilResponse> findAllActive();

    /**
     * Busca un arquetipo activo por identificador.
     *
     * @param id identificador del arquetipo
     * @return arquetipo encontrado
     */
    ArquetipoPerfilResponse findActiveById(UUID id);
}
