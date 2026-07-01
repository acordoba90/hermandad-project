package com.hermandadproject.gestionhermandades.service;

import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadCaracteristicasResponse;

import java.util.UUID;

public interface TipoHermandadCaracteristicasService {
    TipoHermandadCaracteristicasResponse buscarPorTipoHermandadUuid(UUID tipoHermandadUuid);

    TipoHermandadCaracteristicasResponse buscarPorCodigoTipoHermandad(String codigo);
}
