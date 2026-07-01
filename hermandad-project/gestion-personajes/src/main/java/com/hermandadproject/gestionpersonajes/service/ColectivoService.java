package com.hermandadproject.gestionpersonajes.service;

import com.hermandadproject.gestionpersonajes.model.dto.ColectivoCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoResponse;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ColectivoService {
    ColectivoResponse create(ColectivoCreateRequest request);

    ColectivoResponse findById(UUID id);

    ColectivoResponse findByCodigo(String codigo);

    List<ColectivoResponse> findAllActive();

    ColectivoResponse update(UUID id, ColectivoUpdateRequest request);

    void delete(UUID id);
}
