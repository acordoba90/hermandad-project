package com.hermandadproject.gestionhermandades.controller;

import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.service.CarismaHermandadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gestion-hermandades/carismas-hermandad")
public class CarismaHermandadController {

    private final CarismaHermandadService carismaHermandadService;

    public CarismaHermandadController(CarismaHermandadService carismaHermandadService) {
        this.carismaHermandadService = carismaHermandadService;
    }

    @GetMapping
    public ResponseEntity<List<CarismaHermandadDto>> findAll() {
        return ResponseEntity.ok(carismaHermandadService.findAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<CarismaHermandadDto>> findActivos() {
        return ResponseEntity.ok(carismaHermandadService.findActivos());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CarismaHermandadDto> findByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(carismaHermandadService.findByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<CarismaHermandadDto> create(@Valid @RequestBody CarismaHermandadCreateDto dto, UriComponentsBuilder uriBuilder) {
        CarismaHermandadDto created = carismaHermandadService.create(dto);
        return ResponseEntity
                .created(uriBuilder.path("/api/gestion-hermandades/carismas-hermandad/{uuid}")
                        .buildAndExpand(created.uuid())
                        .toUri())
                .body(created);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CarismaHermandadDto> update(@PathVariable UUID uuid, @Valid @RequestBody CarismaHermandadUpdateDto dto) {
        return ResponseEntity.ok(carismaHermandadService.update(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        carismaHermandadService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

