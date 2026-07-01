package com.hermandadproject.gestionciudad.service.impl;

import com.hermandadproject.gestionciudad.exception.IglesiaNotFoundException;
import com.hermandadproject.gestionciudad.mapper.IglesiaMapper;
import com.hermandadproject.gestionciudad.model.dto.IglesiaResponse;
import com.hermandadproject.gestionciudad.model.entity.IglesiaEntity;
import com.hermandadproject.gestionciudad.repository.IglesiaRepository;
import com.hermandadproject.gestionciudad.service.IglesiaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class IglesiaServiceImpl implements IglesiaService {

    private final IglesiaRepository iglesiaRepository;
    private final IglesiaMapper iglesiaMapper;

    public IglesiaServiceImpl(IglesiaRepository iglesiaRepository, IglesiaMapper iglesiaMapper) {
        this.iglesiaRepository = iglesiaRepository;
        this.iglesiaMapper = iglesiaMapper;
    }

    @Override
    public List<IglesiaResponse> listarPorCiudad(UUID ciudadId) {
        return iglesiaRepository.findByCiudadIdAndActivaTrue(ciudadId).stream().map(iglesiaMapper::toResponse).toList();
    }

    @Override
    public List<IglesiaResponse> listarDisponiblesComoSede(UUID ciudadId) {
        return iglesiaRepository.findByCiudadIdAndDisponibleComoSedeTrueAndActivaTrue(ciudadId)
                .stream()
                .map(iglesiaMapper::toResponse)
                .toList();
    }

    @Override
    public List<IglesiaResponse> listarSolaresConstruibles(UUID ciudadId) {
        return iglesiaRepository.findByCiudadIdAndConstruibleTrueAndActivaTrue(ciudadId)
                .stream()
                .map(iglesiaMapper::toResponse)
                .toList();
    }

    @Override
    public IglesiaResponse buscarPorId(UUID id) {
        IglesiaEntity entity = iglesiaRepository.findById(id)
                .orElseThrow(() -> new IglesiaNotFoundException("Iglesia no encontrada"));
        return iglesiaMapper.toResponse(entity);
    }

    @Override
    public IglesiaResponse buscarPorCodigo(String codigo) {
        IglesiaEntity entity = iglesiaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new IglesiaNotFoundException("Iglesia no encontrada"));
        return iglesiaMapper.toResponse(entity);
    }
}
