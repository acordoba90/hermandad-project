package com.hermandadproject.gestionpersonajes.controller;

import com.hermandadproject.gestionpersonajes.model.dto.ColectivoCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoResponse;
import com.hermandadproject.gestionpersonajes.model.dto.ColectivoUpdateRequest;
import com.hermandadproject.gestionpersonajes.service.ColectivoService;
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
@RequestMapping("/api/colectivos")
public class ColectivoController {

    private final ColectivoService colectivoService;

    public ColectivoController(ColectivoService colectivoService) {
        this.colectivoService = colectivoService;
    }

    @PostMapping
    public ResponseEntity<ColectivoResponse> create(
            @Valid @RequestBody ColectivoCreateRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        ColectivoResponse created = colectivoService.create(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/colectivos/{id}").buildAndExpand(created.id()).toUri())
                .body(created);
    }

    @GetMapping
    public List<ColectivoResponse> findAllActive() {
        return colectivoService.findAllActive();
    }

    @GetMapping("/{id}")
    public ColectivoResponse findById(@PathVariable UUID id) {
        return colectivoService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public ColectivoResponse findByCodigo(@PathVariable String codigo) {
        return colectivoService.findByCodigo(codigo);
    }

    @PutMapping("/{id}")
    public ColectivoResponse update(@PathVariable UUID id, @Valid @RequestBody ColectivoUpdateRequest request) {
        return colectivoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        colectivoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
