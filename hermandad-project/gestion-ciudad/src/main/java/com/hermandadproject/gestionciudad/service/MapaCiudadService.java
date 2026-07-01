package com.hermandadproject.gestionciudad.service;

import com.hermandadproject.gestionciudad.model.dto.MapaCiudadResponse;

import java.util.List;
import java.util.UUID;

public interface MapaCiudadService {
    List<MapaCiudadResponse> listarPorCiudad(UUID ciudadId);

    MapaCiudadResponse buscarPorId(UUID id);

    MapaCiudadResponse buscarPorCodigo(String codigo);
}
