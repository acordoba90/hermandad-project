package com.hermandadproject.gestionpasos.service.impl;

import com.hermandadproject.gestionpasos.exception.PasoTemplateNotFoundException;
import com.hermandadproject.gestionpasos.mapper.PasoSlotMapper;
import com.hermandadproject.gestionpasos.model.dto.PasoSlotResponse;
import com.hermandadproject.gestionpasos.repository.PasoSlotRepository;
import com.hermandadproject.gestionpasos.repository.PasoTemplateRepository;
import com.hermandadproject.gestionpasos.service.PasoSlotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PasoSlotServiceImpl implements PasoSlotService {

    private final PasoSlotRepository pasoSlotRepository;
    private final PasoTemplateRepository pasoTemplateRepository;
    private final PasoSlotMapper pasoSlotMapper;

    public PasoSlotServiceImpl(
            PasoSlotRepository pasoSlotRepository,
            PasoTemplateRepository pasoTemplateRepository,
            PasoSlotMapper pasoSlotMapper
    ) {
        this.pasoSlotRepository = pasoSlotRepository;
        this.pasoTemplateRepository = pasoTemplateRepository;
        this.pasoSlotMapper = pasoSlotMapper;
    }

    @Override
    public List<PasoSlotResponse> findSlotsByPasoTemplateId(UUID idPlantillaPaso) {
        if (!pasoTemplateRepository.existsById(idPlantillaPaso)) {
            throw new PasoTemplateNotFoundException("Paso base no encontrado");
        }

        return pasoSlotRepository.findByPlantillaPasoIdAndActivoTrue(idPlantillaPaso)
                .stream()
                .map(pasoSlotMapper::toResponse)
                .toList();
    }
}
