package com.hermandadproject.gestionpasos.service;

import com.hermandadproject.gestionpasos.model.dto.PasoTemplateResponse;

import java.util.List;
import java.util.UUID;

public interface PasoTemplateService {
    List<PasoTemplateResponse> findAllActive();

    PasoTemplateResponse findById(UUID id);

    PasoTemplateResponse findByCodigo(String codigo);
}
