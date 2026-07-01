package com.hermandadproject.gestionciudad.controller;

import com.hermandadproject.gestionciudad.model.dto.IglesiaResponse;
import com.hermandadproject.gestionciudad.service.IglesiaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/iglesias")
public class IglesiaController {

    private final IglesiaService iglesiaService;

    public IglesiaController(IglesiaService iglesiaService) {
        this.iglesiaService = iglesiaService;
    }

    @GetMapping("/{id}")
    public IglesiaResponse buscarPorId(@PathVariable UUID id) {
        return iglesiaService.buscarPorId(id);
    }

    @GetMapping("/codigo/{codigo}")
    public IglesiaResponse buscarPorCodigo(@PathVariable String codigo) {
        return iglesiaService.buscarPorCodigo(codigo);
    }

    @GetMapping("/ciudad/{ciudadId}")
    public List<IglesiaResponse> listarPorCiudad(@PathVariable UUID ciudadId) {
        return iglesiaService.listarPorCiudad(ciudadId);
    }

    @GetMapping("/ciudad/{ciudadId}/sedes-disponibles")
    public List<IglesiaResponse> listarDisponiblesComoSede(@PathVariable UUID ciudadId) {
        return iglesiaService.listarDisponiblesComoSede(ciudadId);
    }

    @GetMapping("/ciudad/{ciudadId}/solares-construibles")
    public List<IglesiaResponse> listarSolaresConstruibles(@PathVariable UUID ciudadId) {
        return iglesiaService.listarSolaresConstruibles(ciudadId);
    }
}
