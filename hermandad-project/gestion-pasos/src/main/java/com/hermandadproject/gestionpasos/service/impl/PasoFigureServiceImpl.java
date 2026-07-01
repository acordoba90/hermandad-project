package com.hermandadproject.gestionpasos.service.impl;

import com.hermandadproject.gestionpasos.exception.PasoFigureNotFoundException;
import com.hermandadproject.gestionpasos.mapper.PasoFigureMapper;
import com.hermandadproject.gestionpasos.model.dto.PasoFigureResponse;
import com.hermandadproject.gestionpasos.model.entity.FiguraPasoEntity;
import com.hermandadproject.gestionpasos.model.enums.FigureTypeEnum;
import com.hermandadproject.gestionpasos.repository.PasoFigureRepository;
import com.hermandadproject.gestionpasos.service.PasoFigureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PasoFigureServiceImpl implements PasoFigureService {

    private final PasoFigureRepository pasoFigureRepository;
    private final PasoFigureMapper pasoFigureMapper;

    public PasoFigureServiceImpl(PasoFigureRepository pasoFigureRepository, PasoFigureMapper pasoFigureMapper) {
        this.pasoFigureRepository = pasoFigureRepository;
        this.pasoFigureMapper = pasoFigureMapper;
    }

    @Override
    public List<PasoFigureResponse> findAllActive() {
        return pasoFigureRepository.findByActivoTrue()
                .stream()
                .map(pasoFigureMapper::toResponse)
                .toList();
    }

    @Override
    public List<PasoFigureResponse> findByType(FigureTypeEnum tipo) {
        return pasoFigureRepository.findByTipoAndActivoTrue(tipo)
                .stream()
                .map(pasoFigureMapper::toResponse)
                .toList();
    }

    @Override
    public PasoFigureResponse findById(UUID id) {
        FiguraPasoEntity entity = pasoFigureRepository.findById(id)
                .orElseThrow(() -> new PasoFigureNotFoundException("Figura de paso no encontrada"));
        return pasoFigureMapper.toResponse(entity);
    }

    @Override
    public PasoFigureResponse findByCodigo(String codigo) {
        FiguraPasoEntity entity = pasoFigureRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PasoFigureNotFoundException("Figura de paso no encontrada"));
        return pasoFigureMapper.toResponse(entity);
    }
}
