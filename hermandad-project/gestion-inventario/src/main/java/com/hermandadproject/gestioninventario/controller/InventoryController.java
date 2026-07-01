package com.hermandadproject.gestioninventario.controller;

import com.hermandadproject.gestioninventario.model.dto.InventoryItemCreateRequest;
import com.hermandadproject.gestioninventario.model.dto.InventoryItemQuantityUpdateRequest;
import com.hermandadproject.gestioninventario.model.dto.InventoryItemResponse;
import com.hermandadproject.gestioninventario.model.enums.InventoryItemTypeEnum;
import com.hermandadproject.gestioninventario.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/items")
    public ResponseEntity<InventoryItemResponse> addItem(
            @Valid @RequestBody InventoryItemCreateRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        InventoryItemResponse created = inventoryService.addItem(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/inventory/items/{id}").buildAndExpand(created.id()).toUri())
                .body(created);
    }

    @GetMapping("/hermandad/{idHermandad}")
    public List<InventoryItemResponse> findByIdHermandad(@PathVariable UUID idHermandad) {
        return inventoryService.findByIdHermandad(idHermandad);
    }

    @GetMapping("/hermandad/{idHermandad}/tipo/{tipoElemento}")
    public List<InventoryItemResponse> findByIdHermandadAndTipo(
            @PathVariable UUID idHermandad,
            @PathVariable InventoryItemTypeEnum tipoElemento
    ) {
        return inventoryService.findByIdHermandadAndTipo(idHermandad, tipoElemento);
    }

    @GetMapping("/hermandad/{idHermandad}/owns/{tipoElemento}/{codigoElemento}")
    public Map<String, Boolean> ownsItem(
            @PathVariable UUID idHermandad,
            @PathVariable InventoryItemTypeEnum tipoElemento,
            @PathVariable String codigoElemento
    ) {
        return Map.of("owns", inventoryService.ownsItem(idHermandad, tipoElemento, codigoElemento));
    }

    @PatchMapping("/items/{id}/cantidad")
    public InventoryItemResponse updateQuantity(
            @PathVariable UUID id,
            @Valid @RequestBody InventoryItemQuantityUpdateRequest request
    ) {
        return inventoryService.updateQuantity(id, request);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID id) {
        inventoryService.removeItem(id);
        return ResponseEntity.noContent().build();
    }
}
