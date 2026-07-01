package com.hermandadproject.gestionciudad.service.impl;

import com.hermandadproject.gestionciudad.exception.NodoCiudadNotFoundException;
import com.hermandadproject.gestionciudad.mapper.NodoCiudadMapper;
import com.hermandadproject.gestionciudad.model.dto.NodoCiudadResponse;
import com.hermandadproject.gestionciudad.model.entity.NodoCiudadEntity;
import com.hermandadproject.gestionciudad.model.enums.TipoNodoCiudadEnum;
import com.hermandadproject.gestionciudad.repository.NodoCiudadRepository;
import com.hermandadproject.gestionciudad.service.NodoCiudadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class NodoCiudadServiceImpl implements NodoCiudadService {

    private final NodoCiudadRepository nodoCiudadRepository;
    private final NodoCiudadMapper nodoCiudadMapper;

    public NodoCiudadServiceImpl(NodoCiudadRepository nodoCiudadRepository, NodoCiudadMapper nodoCiudadMapper) {
        this.nodoCiudadRepository = nodoCiudadRepository;
        this.nodoCiudadMapper = nodoCiudadMapper;
    }

    @Override
    public List<NodoCiudadResponse> listarPorMapa(UUID mapaCiudadId) {
        return nodoCiudadRepository.findByMapaCiudadIdAndActivoTrue(mapaCiudadId).stream().map(nodoCiudadMapper::toResponse).toList();
    }

    @Override
    public List<NodoCiudadResponse> listarPorMapaYTipo(UUID mapaCiudadId, TipoNodoCiudadEnum tipo) {
        return nodoCiudadRepository.findByMapaCiudadIdAndTipoAndActivoTrue(mapaCiudadId, tipo)
                .stream()
                .map(nodoCiudadMapper::toResponse)
                .toList();
    }

    @Override
    public NodoCiudadResponse buscarPorId(UUID id) {
        NodoCiudadEntity entity = nodoCiudadRepository.findById(id)
                .orElseThrow(() -> new NodoCiudadNotFoundException("Nodo de ciudad no encontrado"));
        return nodoCiudadMapper.toResponse(entity);
    }

    @Override
    public NodoCiudadResponse buscarPorCodigo(UUID mapaCiudadId, String codigo) {
        NodoCiudadEntity entity = nodoCiudadRepository.findByMapaCiudadIdAndCodigo(mapaCiudadId, codigo)
                .orElseThrow(() -> new NodoCiudadNotFoundException("Nodo de ciudad no encontrado"));
        return nodoCiudadMapper.toResponse(entity);
    }
}
