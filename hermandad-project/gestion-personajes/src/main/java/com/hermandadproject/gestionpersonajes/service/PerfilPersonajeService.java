package com.hermandadproject.gestionpersonajes.service;

import com.hermandadproject.gestionpersonajes.model.dto.PerfilPersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.PerfilPersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;

import java.util.UUID;

/**
 * Servicio de creacion y consulta de perfiles evolutivos de personajes.
 */
public interface PerfilPersonajeService {
    /**
     * Crea el perfil de un personaje copiando atributos desde un arquetipo activo.
     *
     * @param personaje personaje propietario
     * @param arquetipoPerfilId identificador del arquetipo de origen
     * @return perfil persistido
     */
    PerfilPersonajeEntity crearDesdeArquetipo(PersonajeEntity personaje, UUID arquetipoPerfilId);

    /**
     * Crea el perfil de un personaje existente desde un arquetipo activo.
     *
     * @param personajeId identificador del personaje
     * @param arquetipoPerfilId identificador del arquetipo
     * @return perfil creado
     */
    PerfilPersonajeResponse createFromArquetipo(UUID personajeId, UUID arquetipoPerfilId);

    /**
     * Consulta el perfil asociado a un personaje.
     *
     * @param personajeId identificador del personaje
     * @return perfil encontrado
     */
    PerfilPersonajeResponse findByPersonajeId(UUID personajeId);
}
