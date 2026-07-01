package com.hermandadproject.gestionciudad.service.impl;

import com.hermandadproject.gestionciudad.exception.MapaCiudadNotFoundException;
import com.hermandadproject.gestionciudad.mapper.MapaCiudadMapper;
import com.hermandadproject.gestionciudad.model.dto.MapaCiudadResponse;
import com.hermandadproject.gestionciudad.model.entity.MapaCiudadEntity;
import com.hermandadproject.gestionciudad.repository.MapaCiudadRepository;
import com.hermandadproject.gestionciudad.service.MapaCiudadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MapaCiudadServiceImpl implements MapaCiudadService {

    private final MapaCiudadRepository mapaCiudadRepository;
    private final MapaCiudadMapper mapaCiudadMapper;

    public MapaCiudadServiceImpl(MapaCiudadRepository mapaCiudadRepository, MapaCiudadMapper mapaCiudadMapper) {
        this.mapaCiudadRepository = mapaCiudadRepository;
        this.mapaCiudadMapper = mapaCiudadMapper;
    }

    @Override
    public List<MapaCiudadResponse> listarPorCiudad(UUID ciudadId) {
        return mapaCiudadRepository.findByCiudadIdAndActivoTrue(ciudadId).stream().map(mapaCiudadMapper::toResponse).toList();
    }

    @Override
    public MapaCiudadResponse buscarPorId(UUID id) {
        MapaCiudadEntity entity = mapaCiudadRepository.findById(id)
                .orElseThrow(() -> new MapaCiudadNotFoundException("Mapa de ciudad no encontrado"));
        return mapaCiudadMapper.toResponse(entity);
    }

    @Override
    public MapaCiudadResponse buscarPorCodigo(String codigo) {
        MapaCiudadEntity entity = mapaCiudadRepository.findByCodigo(codigo)
                .orElseThrow(() -> new MapaCiudadNotFoundException("Mapa de ciudad no encontrado"));
        return mapaCiudadMapper.toResponse(entity);
    }
}
