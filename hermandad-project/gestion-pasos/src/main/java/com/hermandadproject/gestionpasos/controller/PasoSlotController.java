package com.hermandadproject.gestionpasos.controller;

import com.hermandadproject.gestionpasos.model.dto.PasoSlotResponse;
import com.hermandadproject.gestionpasos.service.PasoSlotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/paso-slots")
public class PasoSlotController {

    private final PasoSlotService pasoSlotService;

    public PasoSlotController(PasoSlotService pasoSlotService) {
        this.pasoSlotService = pasoSlotService;
    }

    @GetMapping("/template/{idPlantillaPaso}")
    public List<PasoSlotResponse> findSlotsByPasoTemplateId(@PathVariable UUID idPlantillaPaso) {
        return pasoSlotService.findSlotsByPasoTemplateId(idPlantillaPaso);
    }
}
