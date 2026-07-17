package com.hermandadproject.gestionpersonajes.service;

import com.hermandadproject.gestionpersonajes.model.dto.RolPersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;

import java.util.List;
import java.util.UUID;

/**
 * Casos de uso de consulta y validacion del catalogo de roles de personaje.
 */
public interface RolPersonajeService {

    /**
     * Lista todos los roles activos ordenados para presentacion.
     *
     * @return roles activos disponibles
     */
    List<RolPersonajeResponse> findAllActive();

    /**
     * Lista los roles activos asociados a un colectivo activo.
     *
     * @param colectivoId identificador del colectivo
     * @return roles activos del colectivo
     */
    List<RolPersonajeResponse> findActiveByColectivoId(UUID colectivoId);

    /**
     * Consulta un rol activo por identificador.
     *
     * @param id identificador del rol
     * @return DTO del rol activo
     */
    RolPersonajeResponse findById(UUID id);

    /**
     * Obtiene la entidad activa necesaria para operaciones internas de negocio.
     *
     * @param id identificador del rol
     * @return entidad de rol activa
     */
    RolPersonajeEntity findActiveEntityById(UUID id);

    /**
     * Valida que el rol pertenece al colectivo indicado.
     *
     * @param rolPersonaje rol validado previamente
     * @param colectivo colectivo validado previamente
     */
    void validarPertenencia(RolPersonajeEntity rolPersonaje, ColectivoEntity colectivo);
}
