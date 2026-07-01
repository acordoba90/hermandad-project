package com.hermandadproject.gestionhermandades.service;

import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadUpdateDto;

import java.util.List;
import java.util.UUID;

public interface TipoHermandadService {
    List<TipoHermandadDto> findAll();

    List<TipoHermandadDto> findActivos();

    TipoHermandadDto findByUuid(UUID uuid);

    TipoHermandadDto create(TipoHermandadCreateDto dto);

    TipoHermandadDto update(UUID uuid, TipoHermandadUpdateDto dto);

    void delete(UUID uuid);
}

