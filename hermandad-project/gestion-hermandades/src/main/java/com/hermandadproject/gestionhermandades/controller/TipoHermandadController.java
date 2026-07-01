package com.hermandadproject.gestionhermandades.controller;

import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.service.TipoHermandadService;
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
@RequestMapping("/api/gestion-hermandades/tipos-hermandad")
public class TipoHermandadController {

    private final TipoHermandadService tipoHermandadService;

    public TipoHermandadController(TipoHermandadService tipoHermandadService) {
        this.tipoHermandadService = tipoHermandadService;
    }

    @GetMapping
    public ResponseEntity<List<TipoHermandadDto>> findAll() {
        return ResponseEntity.ok(tipoHermandadService.findAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<TipoHermandadDto>> findActivos() {
        return ResponseEntity.ok(tipoHermandadService.findActivos());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TipoHermandadDto> findByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(tipoHermandadService.findByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<TipoHermandadDto> create(@Valid @RequestBody TipoHermandadCreateDto dto, UriComponentsBuilder uriBuilder) {
        TipoHermandadDto created = tipoHermandadService.create(dto);
        return ResponseEntity
                .created(uriBuilder.path("/api/gestion-hermandades/tipos-hermandad/{uuid}")
                        .buildAndExpand(created.uuid())
                        .toUri())
                .body(created);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TipoHermandadDto> update(@PathVariable UUID uuid, @Valid @RequestBody TipoHermandadUpdateDto dto) {
        return ResponseEntity.ok(tipoHermandadService.update(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        tipoHermandadService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}

