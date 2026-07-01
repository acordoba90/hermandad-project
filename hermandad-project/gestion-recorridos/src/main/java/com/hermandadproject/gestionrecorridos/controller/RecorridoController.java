package com.hermandadproject.gestionrecorridos.controller;

import com.hermandadproject.gestionrecorridos.model.dto.ActualizarRecorridoRequest;
import com.hermandadproject.gestionrecorridos.model.dto.CrearRecorridoRequest;
import com.hermandadproject.gestionrecorridos.model.dto.RecorridoResponse;
import com.hermandadproject.gestionrecorridos.model.dto.ValidarRecorridoResponse;
import com.hermandadproject.gestionrecorridos.service.RecorridoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/recorridos")
public class RecorridoController {

    private final RecorridoService recorridoService;

    public RecorridoController(RecorridoService recorridoService) {
        this.recorridoService = recorridoService;
    }

    @PostMapping
    public ResponseEntity<RecorridoResponse> crear(
            @Valid @RequestBody CrearRecorridoRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        RecorridoResponse creado = recorridoService.crear(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/recorridos/{id}").buildAndExpand(creado.id()).toUri())
                .body(creado);
    }

    @GetMapping("/{id}")
    public RecorridoResponse buscarPorId(@PathVariable UUID id) {
        return recorridoService.buscarPorId(id);
    }

    @GetMapping("/hermandad/{idHermandad}")
    public List<RecorridoResponse> listarPorHermandad(@PathVariable UUID idHermandad) {
        return recorridoService.listarPorHermandad(idHermandad);
    }

    @PutMapping("/{id}")
    public RecorridoResponse actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ActualizarRecorridoRequest request
    ) {
        return recorridoService.actualizar(id, request);
    }

    @GetMapping("/{id}/validar")
    public ValidarRecorridoResponse validar(@PathVariable UUID id) {
        return recorridoService.validar(id);
    }

    @PatchMapping("/{id}/activar")
    public RecorridoResponse activar(@PathVariable UUID id) {
        return recorridoService.activar(id);
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        recorridoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        recorridoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
