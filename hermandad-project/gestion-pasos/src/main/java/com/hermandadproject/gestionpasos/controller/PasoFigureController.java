package com.hermandadproject.gestionpasos.controller;

import com.hermandadproject.gestionpasos.model.dto.PasoFigureResponse;
import com.hermandadproject.gestionpasos.model.enums.FigureTypeEnum;
import com.hermandadproject.gestionpasos.service.PasoFigureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/paso-figures")
public class PasoFigureController {

    private final PasoFigureService pasoFigureService;

    public PasoFigureController(PasoFigureService pasoFigureService) {
        this.pasoFigureService = pasoFigureService;
    }

    @GetMapping
    public List<PasoFigureResponse> findAllActive() {
        return pasoFigureService.findAllActive();
    }

    @GetMapping("/{id}")
    public PasoFigureResponse findById(@PathVariable UUID id) {
        return pasoFigureService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public PasoFigureResponse findByCodigo(@PathVariable String codigo) {
        return pasoFigureService.findByCodigo(codigo);
    }

    @GetMapping("/tipo/{tipo}")
    public List<PasoFigureResponse> findByType(@PathVariable FigureTypeEnum tipo) {
        return pasoFigureService.findByType(tipo);
    }
}
