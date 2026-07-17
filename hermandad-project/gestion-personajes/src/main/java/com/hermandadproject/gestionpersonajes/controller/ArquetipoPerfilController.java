package com.hermandadproject.gestionpersonajes.controller;

import com.hermandadproject.gestionpersonajes.model.dto.ArquetipoPerfilResponse;
import com.hermandadproject.gestionpersonajes.service.ArquetipoPerfilService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Endpoints REST de consulta del catalogo de arquetipos de perfil.
 */
@RestController
@RequestMapping("/api/arquetipos-perfil")
public class ArquetipoPerfilController {

    private final ArquetipoPerfilService arquetipoPerfilService;

    public ArquetipoPerfilController(ArquetipoPerfilService arquetipoPerfilService) {
        this.arquetipoPerfilService = arquetipoPerfilService;
    }

    /**
     * Lista los arquetipos activos disponibles.
     *
     * @return arquetipos activos
     */
    @GetMapping
    public List<ArquetipoPerfilResponse> findAllActive() {
        return arquetipoPerfilService.findAllActive();
    }

    /**
     * Obtiene un arquetipo activo por identificador.
     *
     * @param id identificador del arquetipo
     * @return arquetipo encontrado
     */
    @GetMapping("/{id}")
    public ArquetipoPerfilResponse findById(@PathVariable UUID id) {
        return arquetipoPerfilService.findActiveById(id);
    }
}
