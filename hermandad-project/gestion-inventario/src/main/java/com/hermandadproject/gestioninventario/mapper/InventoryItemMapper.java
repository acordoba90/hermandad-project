package com.hermandadproject.gestioninventario.mapper;

import com.hermandadproject.gestioninventario.model.dto.InventoryItemCreateRequest;
import com.hermandadproject.gestioninventario.model.dto.InventoryItemResponse;
import com.hermandadproject.gestioninventario.model.entity.ElementoInventarioEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InventoryItemMapper {

    public ElementoInventarioEntity toEntity(InventoryItemCreateRequest request) {
        ElementoInventarioEntity entity = new ElementoInventarioEntity();
        entity.setIdHermandad(request.idHermandad());
        entity.setTipoElemento(request.tipoElemento());
        entity.setIdElemento(request.idElemento());
        entity.setCodigoElemento(request.codigoElemento());
        entity.setCantidad(request.cantidad());
        entity.setActivo(true);
        entity.setFechaAdquisicion(Instant.now());
        return entity;
    }

    public InventoryItemResponse toResponse(ElementoInventarioEntity entity) {
        return new InventoryItemResponse(
                entity.getId(),
                entity.getIdHermandad(),
                entity.getTipoElemento(),
                entity.getIdElemento(),
                entity.getCodigoElemento(),
                entity.getCantidad(),
                entity.getActivo(),
                entity.getFechaAdquisicion(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
