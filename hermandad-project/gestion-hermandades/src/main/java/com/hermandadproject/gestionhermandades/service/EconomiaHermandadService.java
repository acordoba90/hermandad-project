package com.hermandadproject.gestionhermandades.service;

import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.model.entity.EconomiaHermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.HermandadEntity;

import java.util.UUID;

public interface EconomiaHermandadService {

    EconomiaHermandadDto findByHermandad(UUID uuidHermandad);

    EconomiaHermandadDto update(UUID uuidHermandad, EconomiaHermandadUpdateDto dto);

    EconomiaHermandadEntity crearEconomiaInicial(HermandadEntity hermandad);

    EconomiaHermandadDto recalcularEconomia(UUID uuidHermandad);
}

