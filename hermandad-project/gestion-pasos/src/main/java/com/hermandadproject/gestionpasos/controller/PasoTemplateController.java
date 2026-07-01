package com.hermandadproject.gestionpasos.controller;

import com.hermandadproject.gestionpasos.model.dto.PasoTemplateResponse;
import com.hermandadproject.gestionpasos.service.PasoTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/paso-templates")
public class PasoTemplateController {

    private final PasoTemplateService pasoTemplateService;

    public PasoTemplateController(PasoTemplateService pasoTemplateService) {
        this.pasoTemplateService = pasoTemplateService;
    }

    @GetMapping
    public List<PasoTemplateResponse> findAllActive() {
        return pasoTemplateService.findAllActive();
    }

    @GetMapping("/{id}")
    public PasoTemplateResponse findById(@PathVariable UUID id) {
        return pasoTemplateService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public PasoTemplateResponse findByCodigo(@PathVariable String codigo) {
        return pasoTemplateService.findByCodigo(codigo);
    }
}
