package com.hermandadproject.gestionciudad.controller;

import com.hermandadproject.gestionciudad.model.dto.NodoCiudadResponse;
import com.hermandadproject.gestionciudad.model.enums.TipoNodoCiudadEnum;
import com.hermandadproject.gestionciudad.service.NodoCiudadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nodos-ciudad")
public class NodoCiudadController {

    private final NodoCiudadService nodoCiudadService;

    public NodoCiudadController(NodoCiudadService nodoCiudadService) {
        this.nodoCiudadService = nodoCiudadService;
    }

    @GetMapping("/{id}")
    public NodoCiudadResponse buscarPorId(@PathVariable UUID id) {
        return nodoCiudadService.buscarPorId(id);
    }

    @GetMapping("/mapa/{mapaCiudadId}")
    public List<NodoCiudadResponse> listarPorMapa(@PathVariable UUID mapaCiudadId) {
        return nodoCiudadService.listarPorMapa(mapaCiudadId);
    }

    @GetMapping("/mapa/{mapaCiudadId}/tipo/{tipo}")
    public List<NodoCiudadResponse> listarPorMapaYTipo(
            @PathVariable UUID mapaCiudadId,
            @PathVariable TipoNodoCiudadEnum tipo
    ) {
        return nodoCiudadService.listarPorMapaYTipo(mapaCiudadId, tipo);
    }

    @GetMapping("/mapa/{mapaCiudadId}/codigo/{codigo}")
    public NodoCiudadResponse buscarPorCodigo(@PathVariable UUID mapaCiudadId, @PathVariable String codigo) {
        return nodoCiudadService.buscarPorCodigo(mapaCiudadId, codigo);
    }
}
