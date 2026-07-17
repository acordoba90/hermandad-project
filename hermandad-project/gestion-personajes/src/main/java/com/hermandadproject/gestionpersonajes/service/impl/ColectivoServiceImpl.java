package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ColectivoAlreadyExistsException;
import com.hermandadproject.gestionpersonajes.exception.ColectivoNotFoundException;
import com.hermandadproject.gestionpersonajes.mapper.ColectivoMapper;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoResponse;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoUpdateRequest;
import com.hermandadproject.gestionpersonajes.model.entity.ColectivoEntity;
import com.hermandadproject.gestionpersonajes.repository.ColectivoRepository;
import com.hermandadproject.gestionpersonajes.service.ColectivoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementacion transaccional de los casos de uso de colectivos.
 */
@Service
@Transactional
public class ColectivoServiceImpl implements ColectivoService {

    private final ColectivoRepository colectivoRepository;
    private final ColectivoMapper colectivoMapper;

    public ColectivoServiceImpl(ColectivoRepository colectivoRepository, ColectivoMapper colectivoMapper) {
        this.colectivoRepository = colectivoRepository;
        this.colectivoMapper = colectivoMapper;
    }

    @Override
    public ColectivoResponse create(ColectivoCreateRequest request) {
        if (colectivoRepository.existsByCodigo(request.codigo())) {
            throw new ColectivoAlreadyExistsException("Ya existe un colectivo con ese codigo");
        }

        ColectivoEntity entity = colectivoMapper.toEntity(request);
        entity.setActivo(true);
        ColectivoEntity saved = colectivoRepository.save(entity);
        return colectivoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ColectivoResponse findById(UUID id) {
        return colectivoMapper.toResponse(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ColectivoResponse findByCodigo(String codigo) {
        ColectivoEntity entity = colectivoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ColectivoNotFoundException("Colectivo no encontrado"));
        return colectivoMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColectivoResponse> findAllActive() {
        return colectivoRepository.findByActivoTrueOrderByNombreAsc()
                .stream()
                .map(colectivoMapper::toResponse)
                .toList();
    }

    @Override
    public ColectivoResponse update(UUID id, ColectivoUpdateRequest request) {
        ColectivoEntity entity = findEntityById(id);
        colectivoMapper.updateEntity(entity, request);
        ColectivoEntity saved = colectivoRepository.save(entity);
        return colectivoMapper.toResponse(saved);
    }

    @Override
    public void delete(UUID id) {
        ColectivoEntity entity = findEntityById(id);
        entity.setActivo(false);
        colectivoRepository.save(entity);
    }

    private ColectivoEntity findEntityById(UUID id) {
        return colectivoRepository.findById(id)
                .orElseThrow(() -> new ColectivoNotFoundException("Colectivo no encontrado"));
    }
}
