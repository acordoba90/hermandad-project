package com.hermandadproject.gestioninventario.service;

import com.hermandadproject.gestioninventario.model.dto.InventoryItemCreateRequest;
import com.hermandadproject.gestioninventario.model.dto.InventoryItemQuantityUpdateRequest;
import com.hermandadproject.gestioninventario.model.dto.InventoryItemResponse;
import com.hermandadproject.gestioninventario.model.enums.InventoryItemTypeEnum;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    InventoryItemResponse addItem(InventoryItemCreateRequest request);

    List<InventoryItemResponse> findByIdHermandad(UUID idHermandad);

    List<InventoryItemResponse> findByIdHermandadAndTipo(UUID idHermandad, InventoryItemTypeEnum tipoElemento);

    boolean ownsItem(UUID idHermandad, InventoryItemTypeEnum tipoElemento, String codigoElemento);

    InventoryItemResponse updateQuantity(UUID id, InventoryItemQuantityUpdateRequest request);

    void removeItem(UUID id);
}
