package com.hermandadproject.gestioninventario.service.impl;

import com.hermandadproject.gestioninventario.exception.InvalidInventoryQuantityException;
import com.hermandadproject.gestioninventario.exception.InventoryItemNotFoundException;
import com.hermandadproject.gestioninventario.mapper.InventoryItemMapper;
import com.hermandadproject.gestioninventario.model.dto.InventoryItemCreateRequest;
import com.hermandadproject.gestioninventario.model.dto.InventoryItemQuantityUpdateRequest;
import com.hermandadproject.gestioninventario.model.dto.InventoryItemResponse;
import com.hermandadproject.gestioninventario.model.entity.ElementoInventarioEntity;
import com.hermandadproject.gestioninventario.model.enums.InventoryItemTypeEnum;
import com.hermandadproject.gestioninventario.repository.InventoryItemRepository;
import com.hermandadproject.gestioninventario.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryItemMapper inventoryItemMapper;

    public InventoryServiceImpl(InventoryItemRepository inventoryItemRepository, InventoryItemMapper inventoryItemMapper) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryItemMapper = inventoryItemMapper;
    }

    @Override
    public InventoryItemResponse addItem(InventoryItemCreateRequest request) {
        int cantidad = resolveQuantity(request.cantidad());

        ElementoInventarioEntity entity = inventoryItemRepository
                .findByIdHermandadAndTipoElementoAndCodigoElemento(request.idHermandad(), request.tipoElemento(), request.codigoElemento())
                .map(existing -> applyExistingItem(existing, cantidad))
                .orElseGet(() -> createNewItem(request, cantidad));

        ElementoInventarioEntity saved = inventoryItemRepository.save(entity);
        return inventoryItemMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemResponse> findByIdHermandad(UUID idHermandad) {
        return inventoryItemRepository.findByIdHermandadAndActivoTrue(idHermandad)
                .stream()
                .map(inventoryItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemResponse> findByIdHermandadAndTipo(UUID idHermandad, InventoryItemTypeEnum tipoElemento) {
        return inventoryItemRepository.findByIdHermandadAndTipoElementoAndActivoTrue(idHermandad, tipoElemento)
                .stream()
                .map(inventoryItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean ownsItem(UUID idHermandad, InventoryItemTypeEnum tipoElemento, String codigoElemento) {
        return inventoryItemRepository
                .findByIdHermandadAndTipoElementoAndCodigoElementoAndActivoTrue(idHermandad, tipoElemento, codigoElemento)
                .map(entity -> entity.getCantidad() != null && entity.getCantidad() > 0)
                .orElse(false);
    }

    @Override
    public InventoryItemResponse updateQuantity(UUID id, InventoryItemQuantityUpdateRequest request) {
        int cantidad = resolveRequiredQuantity(request.cantidad());
        ElementoInventarioEntity entity = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException("Item de inventario no encontrado"));
        entity.setCantidad(cantidad);
        ElementoInventarioEntity saved = inventoryItemRepository.save(entity);
        return inventoryItemMapper.toResponse(saved);
    }

    @Override
    public void removeItem(UUID id) {
        ElementoInventarioEntity entity = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new InventoryItemNotFoundException("Item de inventario no encontrado"));
        entity.setActivo(false);
        inventoryItemRepository.save(entity);
    }

    private ElementoInventarioEntity applyExistingItem(ElementoInventarioEntity existing, int cantidad) {
        if (Boolean.TRUE.equals(existing.getActivo())) {
            existing.setCantidad(existing.getCantidad() + cantidad);
        } else {
            existing.setCantidad(cantidad);
            existing.setActivo(true);
            existing.setFechaAdquisicion(Instant.now());
        }
        return existing;
    }

    private ElementoInventarioEntity createNewItem(InventoryItemCreateRequest request, int cantidad) {
        ElementoInventarioEntity entity = inventoryItemMapper.toEntity(request);
        entity.setCantidad(cantidad);
        entity.setActivo(true);
        entity.setFechaAdquisicion(Instant.now());
        return entity;
    }

    private int resolveQuantity(Integer cantidad) {
        return resolveRequiredQuantity(cantidad == null ? 1 : cantidad);
    }

    private int resolveRequiredQuantity(Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new InvalidInventoryQuantityException("La cantidad debe ser mayor que cero");
        }
        return cantidad;
    }
}
