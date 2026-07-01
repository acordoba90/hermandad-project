package com.hermandadproject.gestionciudad.controller;

import com.hermandadproject.gestionciudad.model.dto.CiudadResponse;
import com.hermandadproject.gestionciudad.service.CiudadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    private final CiudadService ciudadService;

    public CiudadController(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    @GetMapping
    public List<CiudadResponse> listarActivas() {
        return ciudadService.listarActivas();
    }

    @GetMapping("/{id}")
    public CiudadResponse buscarPorId(@PathVariable UUID id) {
        return ciudadService.buscarPorId(id);
    }

    @GetMapping("/codigo/{codigo}")
    public CiudadResponse buscarPorCodigo(@PathVariable String codigo) {
        return ciudadService.buscarPorCodigo(codigo);
    }
}
