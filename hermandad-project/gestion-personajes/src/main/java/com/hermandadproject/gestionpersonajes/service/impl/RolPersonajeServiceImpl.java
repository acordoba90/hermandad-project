package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ColectivoInactiveException;
import com.hermandadproject.gestionpersonajes.exception.ColectivoNotFoundException;
import com.hermandadproject.gestionpersonajes.exception.RolNoPerteneceAlColectivoException;
import com.hermandadproject.gestionpersonajes.exception.RolPersonajeNotFoundException;
import com.hermandadproject.gestionpersonajes.mapper.RolPersonajeMapper;
import com.hermandadproject.gestionpersonajes.model.dto.RolPersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.model.entity.RolPersonajeEntity;
import com.hermandadproject.gestionpersonajes.repository.ColectivoRepository;
import com.hermandadproject.gestionpersonajes.repository.RolPersonajeRepository;
import com.hermandadproject.gestionpersonajes.service.RolPersonajeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementacion transaccional de los casos de uso del catalogo de roles de personaje.
 */
@Service
@Transactional(readOnly = true)
public class RolPersonajeServiceImpl implements RolPersonajeService {

    private final RolPersonajeRepository rolPersonajeRepository;
    private final ColectivoRepository colectivoRepository;
    private final RolPersonajeMapper rolPersonajeMapper;

    public RolPersonajeServiceImpl(
            RolPersonajeRepository rolPersonajeRepository,
            ColectivoRepository colectivoRepository,
            RolPersonajeMapper rolPersonajeMapper
    ) {
        this.rolPersonajeRepository = rolPersonajeRepository;
        this.colectivoRepository = colectivoRepository;
        this.rolPersonajeMapper = rolPersonajeMapper;
    }

    @Override
    public List<RolPersonajeResponse> findAllActive() {
        return rolPersonajeRepository.findAllByActivoTrueOrderByNombreAsc()
                .stream()
                .map(rolPersonajeMapper::toResponse)
                .toList();
    }

    @Override
    public List<RolPersonajeResponse> findActiveByColectivoId(UUID colectivoId) {
        ColectivoEntity colectivo = colectivoRepository.findById(colectivoId)
                .orElseThrow(() -> new ColectivoNotFoundException("Colectivo no encontrado"));
        validarColectivoActivo(colectivo);

        return rolPersonajeRepository.findAllByColectivoIdAndActivoTrueOrderByNombreAsc(colectivoId)
                .stream()
                .map(rolPersonajeMapper::toResponse)
                .toList();
    }

    @Override
    public RolPersonajeResponse findById(UUID id) {
        return rolPersonajeMapper.toResponse(findActiveEntityById(id));
    }

    @Override
    public RolPersonajeEntity findActiveEntityById(UUID id) {
        return rolPersonajeRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RolPersonajeNotFoundException("Rol de personaje no encontrado"));
    }

    @Override
    public void validarPertenencia(RolPersonajeEntity rolPersonaje, ColectivoEntity colectivo) {
        if (!rolPersonaje.getColectivo().getId().equals(colectivo.getId())) {
            throw new RolNoPerteneceAlColectivoException(
                    "El rol de personaje no pertenece al colectivo seleccionado"
            );
        }
    }

    private void validarColectivoActivo(ColectivoEntity colectivo) {
        if (!Boolean.TRUE.equals(colectivo.getActivo())) {
            throw new ColectivoInactiveException("El colectivo no esta activo");
        }
    }
}
