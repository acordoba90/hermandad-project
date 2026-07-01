package com.hermandadproject.gestionciudad.controller;

import com.hermandadproject.gestionciudad.model.dto.CarreraOficialResponse;
import com.hermandadproject.gestionciudad.service.CarreraOficialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/carreras-oficiales")
public class CarreraOficialController {

    private final CarreraOficialService carreraOficialService;

    public CarreraOficialController(CarreraOficialService carreraOficialService) {
        this.carreraOficialService = carreraOficialService;
    }

    @GetMapping("/ciudad/{ciudadId}")
    public CarreraOficialResponse buscarPorCiudad(@PathVariable UUID ciudadId) {
        return carreraOficialService.buscarPorCiudad(ciudadId);
    }

    @GetMapping("/mapa/{mapaCiudadId}")
    public CarreraOficialResponse buscarPorMapa(@PathVariable UUID mapaCiudadId) {
        return carreraOficialService.buscarPorMapa(mapaCiudadId);
    }
}
