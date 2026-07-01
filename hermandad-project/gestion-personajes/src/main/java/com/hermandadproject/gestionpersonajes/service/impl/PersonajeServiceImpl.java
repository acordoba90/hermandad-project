package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ColectivoNotFoundException;
import com.hermandadproject.gestionpersonajes.exception.PersonajeAlreadyExistsException;
import com.hermandadproject.gestionpersonajes.exception.PersonajeNotFoundException;
import com.hermandadproject.gestionpersonajes.mapper.PersonajeMapper;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import com.hermandadproject.gestionpersonajes.repository.ColectivoRepository;
import com.hermandadproject.gestionpersonajes.repository.PersonajeRepository;
import com.hermandadproject.gestionpersonajes.service.PersonajeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeRepository personajeRepository;
    private final ColectivoRepository colectivoRepository;
    private final PersonajeMapper personajeMapper;

    public PersonajeServiceImpl(
            PersonajeRepository personajeRepository,
            ColectivoRepository colectivoRepository,
            PersonajeMapper personajeMapper
    ) {
        this.personajeRepository = personajeRepository;
        this.colectivoRepository = colectivoRepository;
        this.personajeMapper = personajeMapper;
    }

    @Override
    public PersonajeResponse create(PersonajeCreateRequest request) {
        if (personajeRepository.existsByCodigo(request.codigo())) {
            throw new PersonajeAlreadyExistsException("Ya existe un personaje con ese codigo");
        }

        ColectivoEntity colectivo = findColectivoById(request.colectivoId());
        PersonajeEntity entity = personajeMapper.toEntity(request, colectivo);
        entity.setActivo(true);
        PersonajeEntity saved = personajeRepository.save(entity);
        return personajeMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonajeResponse findById(UUID id) {
        return personajeMapper.toResponse(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PersonajeResponse findByCodigo(String codigo) {
        PersonajeEntity entity = personajeRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PersonajeNotFoundException("Personaje no encontrado"));
        return personajeMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeResponse> findAllActive() {
        return personajeRepository.findByActivoTrue()
                .stream()
                .map(personajeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeResponse> findByColectivoId(UUID colectivoId) {
        if (!colectivoRepository.existsById(colectivoId)) {
            throw new ColectivoNotFoundException("Colectivo no encontrado");
        }

        return personajeRepository.findByColectivoIdAndActivoTrue(colectivoId)
                .stream()
                .map(personajeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeResponse> findByColectivoCode(String colectivoCode) {
        if (colectivoRepository.findByCodigo(colectivoCode).isEmpty()) {
            throw new ColectivoNotFoundException("Colectivo no encontrado");
        }

        return personajeRepository.findByColectivoCodigoAndActivoTrue(colectivoCode)
                .stream()
                .map(personajeMapper::toResponse)
                .toList();
    }

    @Override
    public PersonajeResponse update(UUID id, PersonajeUpdateRequest request) {
        PersonajeEntity entity = findEntityById(id);
        ColectivoEntity colectivo = findColectivoById(request.colectivoId());
        personajeMapper.updateEntity(entity, request, colectivo);
        PersonajeEntity saved = personajeRepository.save(entity);
        return personajeMapper.toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        PersonajeEntity entity = findEntityById(id);
        entity.setActivo(false);
        personajeRepository.save(entity);
    }

    private PersonajeEntity findEntityById(UUID id) {
        return personajeRepository.findById(id)
                .orElseThrow(() -> new PersonajeNotFoundException("Personaje no encontrado"));
    }

    private ColectivoEntity findColectivoById(UUID id) {
        return colectivoRepository.findById(id)
                .orElseThrow(() -> new ColectivoNotFoundException("Colectivo no encontrado"));
    }
}
