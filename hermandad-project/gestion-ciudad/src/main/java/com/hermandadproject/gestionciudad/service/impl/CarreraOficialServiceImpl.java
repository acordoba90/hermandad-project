package com.hermandadproject.gestionciudad.service.impl;

import com.hermandadproject.gestionciudad.exception.CarreraOficialNotFoundException;
import com.hermandadproject.gestionciudad.mapper.CarreraOficialMapper;
import com.hermandadproject.gestionciudad.model.dto.CarreraOficialResponse;
import com.hermandadproject.gestionciudad.model.entity.CarreraOficialEntity;
import com.hermandadproject.gestionciudad.repository.CarreraOficialRepository;
import com.hermandadproject.gestionciudad.service.CarreraOficialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CarreraOficialServiceImpl implements CarreraOficialService {

    private final CarreraOficialRepository carreraOficialRepository;
    private final CarreraOficialMapper carreraOficialMapper;

    public CarreraOficialServiceImpl(CarreraOficialRepository carreraOficialRepository, CarreraOficialMapper carreraOficialMapper) {
        this.carreraOficialRepository = carreraOficialRepository;
        this.carreraOficialMapper = carreraOficialMapper;
    }

    @Override
    public CarreraOficialResponse buscarPorCiudad(UUID ciudadId) {
        CarreraOficialEntity entity = carreraOficialRepository.findByCiudadIdAndActivaTrue(ciudadId)
                .orElseThrow(() -> new CarreraOficialNotFoundException("Carrera oficial no encontrada"));
        return carreraOficialMapper.toResponse(entity);
    }

    @Override
    public CarreraOficialResponse buscarPorMapa(UUID mapaCiudadId) {
        CarreraOficialEntity entity = carreraOficialRepository.findByMapaCiudadIdAndActivaTrue(mapaCiudadId)
                .orElseThrow(() -> new CarreraOficialNotFoundException("Carrera oficial no encontrada"));
        return carreraOficialMapper.toResponse(entity);
    }
}
