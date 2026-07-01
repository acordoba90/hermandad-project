package com.hermandadproject.gestionciudad.controller;

import com.hermandadproject.gestionciudad.model.dto.ConexionCiudadResponse;
import com.hermandadproject.gestionciudad.service.ConexionCiudadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/conexiones-ciudad")
public class ConexionCiudadController {

    private final ConexionCiudadService conexionCiudadService;

    public ConexionCiudadController(ConexionCiudadService conexionCiudadService) {
        this.conexionCiudadService = conexionCiudadService;
    }

    @GetMapping("/mapa/{mapaCiudadId}")
    public List<ConexionCiudadResponse> listarPorMapa(@PathVariable UUID mapaCiudadId) {
        return conexionCiudadService.listarPorMapa(mapaCiudadId);
    }

    @GetMapping("/nodo/{nodoCiudadId}")
    public List<ConexionCiudadResponse> listarConexionesDeNodo(@PathVariable UUID nodoCiudadId) {
        return conexionCiudadService.listarConexionesDeNodo(nodoCiudadId);
    }
}
