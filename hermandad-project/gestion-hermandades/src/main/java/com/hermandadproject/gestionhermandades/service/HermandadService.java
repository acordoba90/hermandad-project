package com.hermandadproject.gestionhermandades.service;

import com.hermandadproject.gestionhermandades.model.dto.HermandadCreateRequest;
import com.hermandadproject.gestionhermandades.model.dto.HermandadResponse;
import com.hermandadproject.gestionhermandades.model.dto.HermandadResumenDto;
import com.hermandadproject.gestionhermandades.model.dto.HermandadUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface HermandadService {
    HermandadResponse create(HermandadCreateRequest request);

    HermandadResponse findById(UUID id);

    List<HermandadResponse> findByIdUsuario(UUID idUsuario);

    HermandadResponse update(UUID id, HermandadUpdateRequest request);

    void delete(UUID id);

    HermandadResponse recalcularIndicadores(UUID id);

    List<HermandadResumenDto> rankingPrestigio();

    List<HermandadResumenDto> rankingPopularidad();

    List<HermandadResumenDto> rankingDevocion();

    List<HermandadResumenDto> rankingSolemnidad();
}

