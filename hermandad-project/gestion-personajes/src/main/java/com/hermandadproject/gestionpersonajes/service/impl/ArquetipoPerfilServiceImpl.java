package com.hermandadproject.gestionpersonajes.service.impl;

import com.hermandadproject.gestionpersonajes.exception.ArquetipoPerfilNotFoundException;
import com.hermandadproject.gestionpersonajes.mapper.ArquetipoPerfilMapper;
import com.hermandadproject.gestionpersonajes.model.dto.ArquetipoPerfilResponse;
import com.hermandadproject.gestionpersonajes.repository.ArquetipoPerfilRepository;
import com.hermandadproject.gestionpersonajes.service.ArquetipoPerfilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementacion de consulta para el catalogo de arquetipos de perfil.
 */
@Service
@Transactional(readOnly = true)
public class ArquetipoPerfilServiceImpl implements ArquetipoPerfilService {

    private final ArquetipoPerfilRepository arquetipoPerfilRepository;
    private final ArquetipoPerfilMapper arquetipoPerfilMapper;

    public ArquetipoPerfilServiceImpl(
            ArquetipoPerfilRepository arquetipoPerfilRepository,
            ArquetipoPerfilMapper arquetipoPerfilMapper
    ) {
        this.arquetipoPerfilRepository = arquetipoPerfilRepository;
        this.arquetipoPerfilMapper = arquetipoPerfilMapper;
    }

    @Override
    public List<ArquetipoPerfilResponse> findAllActive() {
        return arquetipoPerfilRepository.findAllByActivoTrueOrderByNombreAsc()
                .stream()
                .map(arquetipoPerfilMapper::toResponse)
                .toList();
    }

    @Override
    public ArquetipoPerfilResponse findActiveById(UUID id) {
        return arquetipoPerfilMapper.toResponse(arquetipoPerfilRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ArquetipoPerfilNotFoundException("Arquetipo de perfil no encontrado")));
    }
}
