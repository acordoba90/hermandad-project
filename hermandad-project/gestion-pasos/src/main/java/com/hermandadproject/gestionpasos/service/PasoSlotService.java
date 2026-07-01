package com.hermandadproject.gestionpasos.service;

import com.hermandadproject.gestionpasos.model.dto.PasoSlotResponse;

import java.util.List;
import java.util.UUID;

public interface PasoSlotService {
    List<PasoSlotResponse> findSlotsByPasoTemplateId(UUID idPlantillaPaso);
}
