package com.hermandadproject.gestionhermandades.controller;

import com.hermandadproject.gestionhermandades.model.dto.MovimientoEconomicoHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.MovimientoEconomicoHermandadDto;
import com.hermandadproject.gestionhermandades.service.MovimientoEconomicoHermandadService;
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
@RequestMapping("/api/gestion-hermandades/hermandades/{uuidHermandad}/movimientos-economicos")
public class MovimientoEconomicoHermandadController {

    private final MovimientoEconomicoHermandadService movimientoEconomicoHermandadService;

    public MovimientoEconomicoHermandadController(MovimientoEconomicoHermandadService movimientoEconomicoHermandadService) {
        this.movimientoEconomicoHermandadService = movimientoEconomicoHermandadService;
    }

    @GetMapping
    public ResponseEntity<List<MovimientoEconomicoHermandadDto>> findAll(@PathVariable UUID uuidHermandad) {
        return ResponseEntity.ok(movimientoEconomicoHermandadService.findByHermandad(uuidHermandad));
    }

    @GetMapping("/ingresos")
    public ResponseEntity<List<MovimientoEconomicoHermandadDto>> findIngresos(@PathVariable UUID uuidHermandad) {
        return ResponseEntity.ok(movimientoEconomicoHermandadService.findIngresosByHermandad(uuidHermandad));
    }

    @GetMapping("/gastos")
    public ResponseEntity<List<MovimientoEconomicoHermandadDto>> findGastos(@PathVariable UUID uuidHermandad) {
        return ResponseEntity.ok(movimientoEconomicoHermandadService.findGastosByHermandad(uuidHermandad));
    }

    @PostMapping
    public ResponseEntity<MovimientoEconomicoHermandadDto> create(
            @PathVariable UUID uuidHermandad,
            @Valid @RequestBody MovimientoEconomicoHermandadCreateDto dto,
            UriComponentsBuilder uriBuilder
    ) {
        // El UUID de hermandad viene del path; si viene tambiÃ©n en el body, lo ignoramos.
        MovimientoEconomicoHermandadCreateDto normalized = new MovimientoEconomicoHermandadCreateDto(
                uuidHermandad,
                dto.tipoMovimiento(),
                dto.categoria(),
                dto.concepto(),
                dto.descripcion(),
                dto.importe(),
                dto.fechaMovimiento()
        );
        MovimientoEconomicoHermandadDto created = movimientoEconomicoHermandadService.create(normalized);
        return ResponseEntity
                .created(uriBuilder
                        .path("/api/gestion-hermandades/hermandades/{uuidHermandad}/movimientos-economicos/{uuidMovimiento}")
                        .buildAndExpand(uuidHermandad, created.uuid())
                        .toUri())
                .body(created);
    }

    @DeleteMapping("/{uuidMovimiento}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuidHermandad, @PathVariable UUID uuidMovimiento) {
        movimientoEconomicoHermandadService.delete(uuidMovimiento);
        return ResponseEntity.noContent().build();
    }
}

