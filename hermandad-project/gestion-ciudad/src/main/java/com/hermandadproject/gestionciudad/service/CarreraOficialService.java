package com.hermandadproject.gestionciudad.service;

import com.hermandadproject.gestionciudad.model.dto.CarreraOficialResponse;

import java.util.UUID;

public interface CarreraOficialService {
    CarreraOficialResponse buscarPorCiudad(UUID ciudadId);

    CarreraOficialResponse buscarPorMapa(UUID mapaCiudadId);
}
