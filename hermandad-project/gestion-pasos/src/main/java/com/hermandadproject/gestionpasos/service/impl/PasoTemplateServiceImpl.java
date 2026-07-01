package com.hermandadproject.gestionpasos.service.impl;

import com.hermandadproject.gestionpasos.exception.PasoTemplateNotFoundException;
import com.hermandadproject.gestionpasos.mapper.PasoTemplateMapper;
import com.hermandadproject.gestionpasos.model.dto.PasoTemplateResponse;
import com.hermandadproject.gestionpasos.model.entity.PlantillaPasoEntity;
import com.hermandadproject.gestionpasos.repository.PasoTemplateRepository;
import com.hermandadproject.gestionpasos.service.PasoTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PasoTemplateServiceImpl implements PasoTemplateService {

    private final PasoTemplateRepository pasoTemplateRepository;
    private final PasoTemplateMapper pasoTemplateMapper;

    public PasoTemplateServiceImpl(PasoTemplateRepository pasoTemplateRepository, PasoTemplateMapper pasoTemplateMapper) {
        this.pasoTemplateRepository = pasoTemplateRepository;
        this.pasoTemplateMapper = pasoTemplateMapper;
    }

    @Override
    public List<PasoTemplateResponse> findAllActive() {
        return pasoTemplateRepository.findByActivoTrue()
                .stream()
                .map(pasoTemplateMapper::toResponse)
                .toList();
    }

    @Override
    public PasoTemplateResponse findById(UUID id) {
        PlantillaPasoEntity entity = pasoTemplateRepository.findById(id)
                .orElseThrow(() -> new PasoTemplateNotFoundException("Paso base no encontrado"));
        return pasoTemplateMapper.toResponse(entity);
    }

    @Override
    public PasoTemplateResponse findByCodigo(String codigo) {
        PlantillaPasoEntity entity = pasoTemplateRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PasoTemplateNotFoundException("Paso base no encontrado"));
        return pasoTemplateMapper.toResponse(entity);
    }
}
