package com.hermandadproject.gestionrecorridos.service;

import com.hermandadproject.gestionrecorridos.model.dto.ActualizarRecorridoRequest;
import com.hermandadproject.gestionrecorridos.model.dto.CrearRecorridoRequest;
import com.hermandadproject.gestionrecorridos.model.dto.RecorridoResponse;
import com.hermandadproject.gestionrecorridos.model.dto.ValidarRecorridoResponse;

import java.util.List;
import java.util.UUID;

public interface RecorridoService {
    RecorridoResponse crear(CrearRecorridoRequest request);

    RecorridoResponse buscarPorId(UUID id);

    List<RecorridoResponse> listarPorHermandad(UUID idHermandad);

    RecorridoResponse actualizar(UUID id, ActualizarRecorridoRequest request);

    ValidarRecorridoResponse validar(UUID id);

    RecorridoResponse activar(UUID id);

    void desactivar(UUID id);

    void eliminar(UUID id);
}
