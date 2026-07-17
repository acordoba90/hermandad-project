package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ArquetipoPerfilInactiveException;
import com.hermandadproject.gestionpersonajes.exception.ArquetipoPerfilNotFoundException;
import com.hermandadproject.gestionpersonajes.exception.PerfilPersonajeAlreadyExistsException;
import com.hermandadproject.gestionpersonajes.exception.PerfilPersonajeAttributeOutOfRangeException;
import com.hermandadproject.gestionpersonajes.exception.PerfilPersonajeNotFoundException;
import com.hermandadproject.gestionpersonajes.exception.PersonajeNotFoundException;
import com.hermandadproject.gestionpersonajes.mapper.PerfilPersonajeMapper;
import com.hermandadproject.gestionpersonajes.model.dto.PerfilPersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ArquetipoPerfilEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PerfilPersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import com.hermandadproject.gestionpersonajes.repository.ArquetipoPerfilRepository;
import com.hermandadproject.gestionpersonajes.repository.PerfilPersonajeRepository;
import com.hermandadproject.gestionpersonajes.repository.PersonajeRepository;
import com.hermandadproject.gestionpersonajes.service.PerfilPersonajeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementacion transaccional de perfiles jugables de personajes.
 */
@Service
@Transactional
public class PerfilPersonajeServiceImpl implements PerfilPersonajeService {

    private final PerfilPersonajeRepository perfilPersonajeRepository;
    private final PersonajeRepository personajeRepository;
    private final ArquetipoPerfilRepository arquetipoPerfilRepository;
    private final PerfilPersonajeMapper perfilPersonajeMapper;

    public PerfilPersonajeServiceImpl(
            PerfilPersonajeRepository perfilPersonajeRepository,
            PersonajeRepository personajeRepository,
            ArquetipoPerfilRepository arquetipoPerfilRepository,
            PerfilPersonajeMapper perfilPersonajeMapper
    ) {
        this.perfilPersonajeRepository = perfilPersonajeRepository;
        this.personajeRepository = personajeRepository;
        this.arquetipoPerfilRepository = arquetipoPerfilRepository;
        this.perfilPersonajeMapper = perfilPersonajeMapper;
    }

    @Override
    public PerfilPersonajeEntity crearDesdeArquetipo(PersonajeEntity personaje, UUID arquetipoPerfilId) {
        if (perfilPersonajeRepository.existsByPersonajeId(personaje.getId())) {
            throw new PerfilPersonajeAlreadyExistsException("El personaje ya tiene perfil");
        }

        ArquetipoPerfilEntity arquetipo = arquetipoPerfilRepository.findById(arquetipoPerfilId)
                .orElseThrow(() -> new ArquetipoPerfilNotFoundException("Arquetipo de perfil no encontrado"));
        if (!Boolean.TRUE.equals(arquetipo.getActivo())) {
            throw new ArquetipoPerfilInactiveException("El arquetipo de perfil esta inactivo");
        }
        validarAtributosBase(arquetipo);

        PerfilPersonajeEntity perfil = perfilPersonajeMapper.crearDesdeArquetipo(personaje, arquetipo);
        PerfilPersonajeEntity saved = perfilPersonajeRepository.save(perfil);
        personaje.setPerfil(saved);
        return saved;
    }

    @Override
    public PerfilPersonajeResponse createFromArquetipo(UUID personajeId, UUID arquetipoPerfilId) {
        PersonajeEntity personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new PersonajeNotFoundException("Personaje no encontrado"));
        return perfilPersonajeMapper.toResponse(crearDesdeArquetipo(personaje, arquetipoPerfilId));
    }

    @Override
    @Transactional(readOnly = true)
    public PerfilPersonajeResponse findByPersonajeId(UUID personajeId) {
        return perfilPersonajeMapper.toResponse(perfilPersonajeRepository.findByPersonajeId(personajeId)
                .orElseThrow(() -> new PerfilPersonajeNotFoundException("Perfil de personaje no encontrado")));
    }

    private void validarAtributosBase(ArquetipoPerfilEntity arquetipo) {
        List<Integer> valores = List.of(
                arquetipo.getLiderazgoBase(),
                arquetipo.getCarismaBase(),
                arquetipo.getDiplomaciaBase(),
                arquetipo.getOrganizacionBase(),
                arquetipo.getComunicacionBase(),
                arquetipo.getInfluenciaBase(),
                arquetipo.getConocimientoCofradeBase(),
                arquetipo.getProtocoloBase(),
                arquetipo.getDevocionBase(),
                arquetipo.getDisciplinaBase(),
                arquetipo.getEmpatiaBase(),
                arquetipo.getLealtadBase(),
                arquetipo.getIntegridadBase(),
                arquetipo.getAmbicionBase(),
                arquetipo.getConflictividadBase(),
                arquetipo.getPopularidadBase(),
                arquetipo.getReputacionBase()
        );
        boolean invalid = valores.stream().anyMatch(value -> value == null || value < 0 || value > 100);
        if (invalid) {
            throw new PerfilPersonajeAttributeOutOfRangeException("Los atributos del arquetipo deben estar entre 0 y 100");
        }
    }
}
