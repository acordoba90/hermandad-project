package com.hermandadproject.gestionpersonajes.controller;

import com.hermandadproject.gestionpersonajes.model.dto.PersonajeCreateRequest;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeResponse;
import com.hermandadproject.gestionpersonajes.model.dto.PersonajeUpdateRequest;
import com.hermandadproject.gestionpersonajes.service.PersonajeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeController {

    private final PersonajeService personajeService;

    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @PostMapping
    public ResponseEntity<PersonajeResponse> create(
            @Valid @RequestBody PersonajeCreateRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        PersonajeResponse created = personajeService.create(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/personajes/{id}").buildAndExpand(created.id()).toUri())
                .body(created);
    }

    @GetMapping
    public List<PersonajeResponse> findAllActive() {
        return personajeService.findAllActive();
    }

    @GetMapping("/{id}")
    public PersonajeResponse findById(@PathVariable UUID id) {
        return personajeService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public PersonajeResponse findByCodigo(@PathVariable String codigo) {
        return personajeService.findByCodigo(codigo);
    }

    @GetMapping("/colectivo/{colectivoId}")
    public List<PersonajeResponse> findByColectivoId(@PathVariable UUID colectivoId) {
        return personajeService.findByColectivoId(colectivoId);
    }

    @GetMapping("/colectivo/codigo/{colectivoCode}")
    public List<PersonajeResponse> findByColectivoCode(@PathVariable String colectivoCode) {
        return personajeService.findByColectivoCode(colectivoCode);
    }

    @PutMapping("/{id}")
    public PersonajeResponse update(@PathVariable UUID id, @Valid @RequestBody PersonajeUpdateRequest request) {
        return personajeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        personajeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
