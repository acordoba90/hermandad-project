package com.hermandadproject.gestionciudad.controller;

import com.hermandadproject.gestionciudad.model.dto.MapaCiudadResponse;
import com.hermandadproject.gestionciudad.service.MapaCiudadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/mapas-ciudad")
public class MapaCiudadController {

    private final MapaCiudadService mapaCiudadService;

    public MapaCiudadController(MapaCiudadService mapaCiudadService) {
        this.mapaCiudadService = mapaCiudadService;
    }

    @GetMapping("/{id}")
    public MapaCiudadResponse buscarPorId(@PathVariable UUID id) {
        return mapaCiudadService.buscarPorId(id);
    }

    @GetMapping("/codigo/{codigo}")
    public MapaCiudadResponse buscarPorCodigo(@PathVariable String codigo) {
        return mapaCiudadService.buscarPorCodigo(codigo);
    }

    @GetMapping("/ciudad/{ciudadId}")
    public List<MapaCiudadResponse> listarPorCiudad(@PathVariable UUID ciudadId) {
        return mapaCiudadService.listarPorCiudad(ciudadId);
    }
}
