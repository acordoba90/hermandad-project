package com.hermandadproject.gestionhermandades.service;

import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadUpdateDto;

import java.util.List;
import java.util.UUID;

public interface CarismaHermandadService {
    List<CarismaHermandadDto> findAll();

    List<CarismaHermandadDto> findActivos();

    CarismaHermandadDto findByUuid(UUID uuid);

    CarismaHermandadDto create(CarismaHermandadCreateDto dto);

    CarismaHermandadDto update(UUID uuid, CarismaHermandadUpdateDto dto);

    void delete(UUID uuid);
}

