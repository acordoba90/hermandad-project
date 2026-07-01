package com.hermandadproject.gestionciudad.service;

import com.hermandadproject.gestionciudad.model.dto.IglesiaResponse;

import java.util.List;
import java.util.UUID;

public interface IglesiaService {
    List<IglesiaResponse> listarPorCiudad(UUID ciudadId);

    List<IglesiaResponse> listarDisponiblesComoSede(UUID ciudadId);

    List<IglesiaResponse> listarSolaresConstruibles(UUID ciudadId);

    IglesiaResponse buscarPorId(UUID id);

    IglesiaResponse buscarPorCodigo(String codigo);
}
