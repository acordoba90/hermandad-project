package com.hermandadproject.gestionciudad.service;

import com.hermandadproject.gestionciudad.model.dto.ConexionCiudadResponse;

import java.util.List;
import java.util.UUID;

public interface ConexionCiudadService {
    List<ConexionCiudadResponse> listarPorMapa(UUID mapaCiudadId);

    List<ConexionCiudadResponse> listarConexionesDeNodo(UUID nodoCiudadId);
}
