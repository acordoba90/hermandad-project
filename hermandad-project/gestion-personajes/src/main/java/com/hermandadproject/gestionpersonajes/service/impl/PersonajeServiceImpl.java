package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ColectivoInactiveException;
import com.hermandadproject.gestionpersonajes.exception.ColectivoNotFoundException;
import com.hermandadproject.gestionpersonajes.exception.PersonajeAlreadyExistsException;
import com.hermandadproject.gestionpersonajes.exception.PersonajeNotFoundException;
import com.hermandadproject.gestionpersonajes.mapper.PersonajeMapper;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.PersonajeEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import com.hermandadproject.gestionpersonajes.repository.ColectivoRepository;
import com.hermandadproject.gestionpersonajes.repository.PersonajeRepository;
import com.hermandadproject.gestionpersonajes.service.PerfilPersonajeService;
import com.hermandadproject.gestionpersonajes.service.PersonajeService;
import com.hermandadproject.gestionpersonajes.service.RolPersonajeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PersonajeServiceImpl implements PersonajeService {

    private static final String COLECTIVO_JUNTA_GOBIERNO = "JUNTA_GOBIERNO";
    private static final String ROL_HERMANO_MAYOR = "HERMANO_MAYOR";

    private final PersonajeRepository personajeRepository;
    private final ColectivoRepository colectivoRepository;
    private final PersonajeMapper personajeMapper;
    private final PerfilPersonajeService perfilPersonajeService;
    private final RolPersonajeService rolPersonajeService;

    public PersonajeServiceImpl(
            PersonajeRepository personajeRepository,
            ColectivoRepository colectivoRepository,
            PersonajeMapper personajeMapper,
            PerfilPersonajeService perfilPersonajeService,
            RolPersonajeService rolPersonajeService
    ) {
        this.personajeRepository = personajeRepository;
        this.colectivoRepository = colectivoRepository;
        this.personajeMapper = personajeMapper;
        this.perfilPersonajeService = perfilPersonajeService;
        this.rolPersonajeService = rolPersonajeService;
    }

    @Override
    public PersonajeResponse create(PersonajeCreateRequest request) {
        if (personajeRepository.existsByCodigo(request.codigo())) {
            throw new PersonajeAlreadyExistsException("Ya existe un personaje con ese codigo");
        }

        ColectivoEntity colectivo = findActiveColectivoById(request.colectivoId());
        RolPersonajeEntity rolPersonaje = rolPersonajeService.findActiveEntityById(request.rolPersonajeId());
        rolPersonajeService.validarPertenencia(rolPersonaje, colectivo);
        PersonajeEntity entity = personajeMapper.toEntity(request, colectivo, rolPersonaje);
        entity.setActivo(true);
        PersonajeEntity saved = personajeRepository.save(entity);
        if (request.arquetipoPerfilId() != null) {
            perfilPersonajeService.crearDesdeArquetipo(saved, request.arquetipoPerfilId());
        }
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
    @Transactional(readOnly = true)
    public List<PersonajeResponse> findHermanosMayoresPredefinidos() {
        return personajeRepository.findPredefinidosByColectivoAndRol(
                        COLECTIVO_JUNTA_GOBIERNO,
                        ROL_HERMANO_MAYOR
                )
                .stream()
                .map(personajeMapper::toResponse)
                .toList();
    }

    @Override
    public PersonajeResponse update(UUID id, PersonajeUpdateRequest request) {
        PersonajeEntity entity = findEntityById(id);
        ColectivoEntity colectivo = findActiveColectivoById(request.colectivoId());
        RolPersonajeEntity rolPersonaje = rolPersonajeService.findActiveEntityById(request.rolPersonajeId());
        rolPersonajeService.validarPertenencia(rolPersonaje, colectivo);
        personajeMapper.updateEntity(entity, request, colectivo, rolPersonaje);
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

    private ColectivoEntity findActiveColectivoById(UUID id) {
        ColectivoEntity colectivo = findColectivoById(id);
        if (!Boolean.TRUE.equals(colectivo.getActivo())) {
            throw new ColectivoInactiveException("El colectivo no esta activo");
        }
        return colectivo;
    }

}
