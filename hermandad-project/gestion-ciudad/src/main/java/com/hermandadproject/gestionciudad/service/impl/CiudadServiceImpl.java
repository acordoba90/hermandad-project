package com.hermandadproject.gestionciudad.service.impl;

import com.hermandadproject.gestionciudad.exception.CiudadNotFoundException;
import com.hermandadproject.gestionciudad.mapper.CiudadMapper;
import com.hermandadproject.gestionciudad.model.dto.CiudadResponse;
import com.hermandadproject.gestionciudad.model.entity.CiudadEntity;
import com.hermandadproject.gestionciudad.repository.CiudadRepository;
import com.hermandadproject.gestionciudad.service.CiudadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CiudadServiceImpl implements CiudadService {

    private final CiudadRepository ciudadRepository;
    private final CiudadMapper ciudadMapper;

    public CiudadServiceImpl(CiudadRepository ciudadRepository, CiudadMapper ciudadMapper) {
        this.ciudadRepository = ciudadRepository;
        this.ciudadMapper = ciudadMapper;
    }

    @Override
    public List<CiudadResponse> listarActivas() {
        return ciudadRepository.findByActivaTrue().stream().map(ciudadMapper::toResponse).toList();
    }

    @Override
    public CiudadResponse buscarPorId(UUID id) {
        CiudadEntity entity = ciudadRepository.findById(id)
                .orElseThrow(() -> new CiudadNotFoundException("Ciudad no encontrada"));
        return ciudadMapper.toResponse(entity);
    }

    @Override
    public CiudadResponse buscarPorCodigo(String codigo) {
        CiudadEntity entity = ciudadRepository.findByCodigo(codigo)
                .orElseThrow(() -> new CiudadNotFoundException("Ciudad no encontrada"));
        return ciudadMapper.toResponse(entity);
    }
}
