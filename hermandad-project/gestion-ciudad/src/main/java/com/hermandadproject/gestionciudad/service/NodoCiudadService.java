package com.hermandadproject.gestionciudad.service;

import com.hermandadproject.gestionciudad.model.dto.NodoCiudadResponse;
import com.hermandadproject.gestionciudad.model.enums.TipoNodoCiudadEnum;

import java.util.List;
import java.util.UUID;

public interface NodoCiudadService {
    List<NodoCiudadResponse> listarPorMapa(UUID mapaCiudadId);

    List<NodoCiudadResponse> listarPorMapaYTipo(UUID mapaCiudadId, TipoNodoCiudadEnum tipo);

    NodoCiudadResponse buscarPorId(UUID id);

    NodoCiudadResponse buscarPorCodigo(UUID mapaCiudadId, String codigo);
}
