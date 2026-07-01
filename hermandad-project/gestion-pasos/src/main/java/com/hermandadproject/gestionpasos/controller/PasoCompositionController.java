package com.hermandadproject.gestionpasos.controller;

import com.hermandadproject.gestionpasos.model.dto.PlacedFigureRequest;
import com.hermandadproject.gestionpasos.model.dto.PlacedFigureResponse;
import com.hermandadproject.gestionpasos.service.PasoCompositionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/paso-compositions")
public class PasoCompositionController {

    private final PasoCompositionService pasoCompositionService;

    public PasoCompositionController(PasoCompositionService pasoCompositionService) {
        this.pasoCompositionService = pasoCompositionService;
    }

    @PostMapping("/place")
    public ResponseEntity<PlacedFigureResponse> placeFigure(
            @Valid @RequestBody PlacedFigureRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        PlacedFigureResponse created = pasoCompositionService.placeFigure(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/paso-compositions/{placedFigureId}").buildAndExpand(created.id()).toUri())
                .body(created);
    }

    @GetMapping("/hermandad/{idHermandad}/template/{idPlantillaPaso}")
    public List<PlacedFigureResponse> findComposition(@PathVariable UUID idHermandad, @PathVariable UUID idPlantillaPaso) {
        return pasoCompositionService.findComposition(idHermandad, idPlantillaPaso);
    }

    @DeleteMapping("/{placedFigureId}")
    public ResponseEntity<Void> removePlacedFigure(@PathVariable UUID placedFigureId) {
        pasoCompositionService.removePlacedFigure(placedFigureId);
        return ResponseEntity.noContent().build();
    }
}
