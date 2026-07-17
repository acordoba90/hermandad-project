package com.hermandadproject.gestionpersonajes.controller;

import com.hermandadproject.gestionpersonajes.model.dto.CrearPerfilDesdeArquetipoRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PerfilPersonajeResponse;
import com.hermandadproject.gestionpersonajes.service.PerfilPersonajeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

/**
 * Endpoints REST para consultar y crear perfiles jugables de personajes.
 */
@RestController
@RequestMapping("/api/personajes/{personajeId}/perfil")
public class PerfilPersonajeController {

    private final PerfilPersonajeService perfilPersonajeService;

    public PerfilPersonajeController(PerfilPersonajeService perfilPersonajeService) {
        this.perfilPersonajeService = perfilPersonajeService;
    }

    /**
     * Consulta el perfil de un personaje.
     *
     * @param personajeId identificador del personaje
     * @return perfil encontrado
     */
    @GetMapping
    public PerfilPersonajeResponse findByPersonajeId(@PathVariable UUID personajeId) {
        return perfilPersonajeService.findByPersonajeId(personajeId);
    }

    /**
     * Crea manualmente el perfil de un personaje desde un arquetipo.
     *
     * @param personajeId identificador del personaje
     * @param request arquetipo seleccionado
     * @param uriBuilder constructor de URI
     * @return perfil creado
     */
    @PostMapping
    public ResponseEntity<PerfilPersonajeResponse> createFromArquetipo(
            @PathVariable UUID personajeId,
            @Valid @RequestBody CrearPerfilDesdeArquetipoRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        PerfilPersonajeResponse created = perfilPersonajeService.createFromArquetipo(
                personajeId,
                request.arquetipoPerfilId()
        );
        return ResponseEntity
                .created(uriBuilder.path("/api/personajes/{personajeId}/perfil").buildAndExpand(personajeId).toUri())
                .body(created);
    }
}
