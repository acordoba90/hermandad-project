package com.hermandadproject.gestionhermandades.controller;

import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.service.EconomiaHermandadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/gestion-hermandades/hermandades/{uuidHermandad}/economia")
public class EconomiaHermandadController {

    private final EconomiaHermandadService economiaHermandadService;

    public EconomiaHermandadController(EconomiaHermandadService economiaHermandadService) {
        this.economiaHermandadService = economiaHermandadService;
    }

    @GetMapping
    public ResponseEntity<EconomiaHermandadDto> findByHermandad(@PathVariable UUID uuidHermandad) {
        return ResponseEntity.ok(economiaHermandadService.findByHermandad(uuidHermandad));
    }

    @PutMapping
    public ResponseEntity<EconomiaHermandadDto> update(@PathVariable UUID uuidHermandad, @Valid @RequestBody EconomiaHermandadUpdateDto dto) {
        return ResponseEntity.ok(economiaHermandadService.update(uuidHermandad, dto));
    }

    @PostMapping("/recalcular")
    public ResponseEntity<EconomiaHermandadDto> recalcular(@PathVariable UUID uuidHermandad) {
        return ResponseEntity.ok(economiaHermandadService.recalcularEconomia(uuidHermandad));
    }
}

