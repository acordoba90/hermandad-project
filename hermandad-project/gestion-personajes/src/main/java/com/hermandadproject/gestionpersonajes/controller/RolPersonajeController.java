package com.hermandadproject.gestionpersonajes.controller;

import com.hermandadproject.gestionpersonajes.model.dto.RolPersonajeResponse;
import com.hermandadproject.gestionpersonajes.service.RolPersonajeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Endpoints REST de solo lectura para consultar roles de personaje activos.
 */
@RestController
@RequestMapping("/api")
public class RolPersonajeController {

    private final RolPersonajeService rolPersonajeService;

    public RolPersonajeController(RolPersonajeService rolPersonajeService) {
        this.rolPersonajeService = rolPersonajeService;
    }

    /**
     * Lista todos los roles activos del catalogo.
     *
     * @return roles activos ordenados por nombre
     */
    @GetMapping("/roles-personaje")
    public List<RolPersonajeResponse> findAllActive() {
        return rolPersonajeService.findAllActive();
    }

    /**
     * Obtiene un rol activo por identificador.
     *
     * @param id identificador del rol
     * @return rol activo
     */
    @GetMapping("/roles-personaje/{id}")
    public RolPersonajeResponse findById(@PathVariable UUID id) {
        return rolPersonajeService.findById(id);
    }

    /**
     * Lista los roles activos asociados a un colectivo.
     *
     * @param colectivoId identificador del colectivo
     * @return roles activos del colectivo
     */
    @GetMapping("/colectivos/{colectivoId}/roles")
    public List<RolPersonajeResponse> findActiveByColectivoId(@PathVariable UUID colectivoId) {
        return rolPersonajeService.findActiveByColectivoId(colectivoId);
    }
}
