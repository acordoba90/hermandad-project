package com.hermandadproject.gestionciudad.service;

import com.hermandadproject.gestionciudad.model.dto.CiudadResponse;

import java.util.List;
import java.util.UUID;

public interface CiudadService {
    List<CiudadResponse> listarActivas();

    CiudadResponse buscarPorId(UUID id);

    CiudadResponse buscarPorCodigo(String codigo);
}
