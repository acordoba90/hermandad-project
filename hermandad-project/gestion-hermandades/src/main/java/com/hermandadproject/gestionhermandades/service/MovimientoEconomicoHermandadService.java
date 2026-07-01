package com.hermandadproject.gestionhermandades.service;

import com.hermandadproject.gestionhermandades.model.dto.MovimientoEconomicoHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.MovimientoEconomicoHermandadDto;

import java.util.List;
import java.util.UUID;

public interface MovimientoEconomicoHermandadService {
    MovimientoEconomicoHermandadDto create(MovimientoEconomicoHermandadCreateDto dto);

    List<MovimientoEconomicoHermandadDto> findByHermandad(UUID uuidHermandad);

    List<MovimientoEconomicoHermandadDto> findIngresosByHermandad(UUID uuidHermandad);

    List<MovimientoEconomicoHermandadDto> findGastosByHermandad(UUID uuidHermandad);

    void delete(UUID uuidMovimiento);
}

