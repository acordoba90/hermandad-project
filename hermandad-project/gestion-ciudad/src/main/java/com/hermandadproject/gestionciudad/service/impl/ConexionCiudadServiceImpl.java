package com.hermandadproject.gestionciudad.service.impl;

import com.hermandadproject.gestionciudad.mapper.ConexionCiudadMapper;
import com.hermandadproject.gestionciudad.model.dto.ConexionCiudadResponse;
import com.hermandadproject.gestionciudad.repository.ConexionCiudadRepository;
import com.hermandadproject.gestionciudad.service.ConexionCiudadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ConexionCiudadServiceImpl implements ConexionCiudadService {

    private final ConexionCiudadRepository conexionCiudadRepository;
    private final ConexionCiudadMapper conexionCiudadMapper;

    public ConexionCiudadServiceImpl(ConexionCiudadRepository conexionCiudadRepository, ConexionCiudadMapper conexionCiudadMapper) {
        this.conexionCiudadRepository = conexionCiudadRepository;
        this.conexionCiudadMapper = conexionCiudadMapper;
    }

    @Override
    public List<ConexionCiudadResponse> listarPorMapa(UUID mapaCiudadId) {
        return conexionCiudadRepository.findByMapaCiudadIdAndActivaTrue(mapaCiudadId)
                .stream()
                .map(conexionCiudadMapper::toResponse)
                .toList();
    }

    @Override
    public List<ConexionCiudadResponse> listarConexionesDeNodo(UUID nodoCiudadId) {
        return conexionCiudadRepository.findByNodoOrigenIdOrNodoDestinoId(nodoCiudadId, nodoCiudadId)
                .stream()
                .map(conexionCiudadMapper::toResponse)
                .toList();
    }
}
