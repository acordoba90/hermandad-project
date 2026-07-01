package com.hermandadproject.gestionhermandades.controller;

import com.hermandadproject.gestionhermandades.model.dto.HermandadCreateRequest;
import com.hermandadproject.gestionhermandades.model.dto.HermandadResponse;
import com.hermandadproject.gestionhermandades.model.dto.HermandadResumenDto;
import com.hermandadproject.gestionhermandades.model.dto.HermandadUpdateRequest;
import com.hermandadproject.gestionhermandades.service.HermandadService;
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
@RequestMapping("/api/gestion-hermandades/hermandades")
public class HermandadController {

    private final HermandadService hermandadService;

    public HermandadController(HermandadService hermandadService) {
        this.hermandadService = hermandadService;
    }

    @PostMapping
    public ResponseEntity<HermandadResponse> create(
            @Valid @RequestBody HermandadCreateRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        HermandadResponse created = hermandadService.create(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/gestion-hermandades/hermandades/{id}").buildAndExpand(created.id()).toUri())
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HermandadResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(hermandadService.findById(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<HermandadResponse>> findByIdUsuario(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(hermandadService.findByIdUsuario(idUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HermandadResponse> update(@PathVariable UUID id, @Valid @RequestBody HermandadUpdateRequest request) {
        return ResponseEntity.ok(hermandadService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        hermandadService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/recalcular-indicadores")
    public ResponseEntity<HermandadResponse> recalcularIndicadores(@PathVariable UUID id) {
        return ResponseEntity.ok(hermandadService.recalcularIndicadores(id));
    }

    @GetMapping("/ranking/prestigio")
    public ResponseEntity<List<HermandadResumenDto>> rankingPrestigio() {
        return ResponseEntity.ok(hermandadService.rankingPrestigio());
    }

    @GetMapping("/ranking/popularidad")
    public ResponseEntity<List<HermandadResumenDto>> rankingPopularidad() {
        return ResponseEntity.ok(hermandadService.rankingPopularidad());
    }

    @GetMapping("/ranking/devocion")
    public ResponseEntity<List<HermandadResumenDto>> rankingDevocion() {
        return ResponseEntity.ok(hermandadService.rankingDevocion());
    }

    @GetMapping("/ranking/solemnidad")
    public ResponseEntity<List<HermandadResumenDto>> rankingSolemnidad() {
        return ResponseEntity.ok(hermandadService.rankingSolemnidad());
    }
}

