package com.hermandadproject.gestionrecorridos.mapper;

import com.hermandadproject.gestionrecorridos.model.dto.RecorridoNodoResponse;
import com.hermandadproject.gestionrecorridos.model.dto.RecorridoResponse;
import com.hermandadproject.gestionrecorridos.model.entity.RecorridoEntity;
import com.hermandadproject.gestionrecorridos.model.entity.RecorridoNodoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecorridoMapper {

    public RecorridoResponse toResponse(RecorridoEntity entity, List<RecorridoNodoEntity> nodos) {
        return new RecorridoResponse(
                entity.getId(),
                entity.getIdHermandad(),
                entity.getIdCiudad(),
                entity.getIdMapaCiudad(),
                entity.getIdIglesiaSede(),
                entity.getIdNodoInicio(),
                entity.getIdNodoFin(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getEstado(),
                entity.getDistanciaTotalMetros(),
                entity.getMinutosEstimados(),
                entity.getDificultadTotal(),
                entity.getPasaCarreraOficial(),
                entity.getActivo(),
                nodos.stream().map(this::toNodoResponse).toList(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    public RecorridoNodoResponse toNodoResponse(RecorridoNodoEntity entity) {
        return new RecorridoNodoResponse(
                entity.getId(),
                entity.getIdNodoCiudad(),
                entity.getCodigoNodo(),
                entity.getNombreNodo(),
                entity.getOrden(),
                entity.getMinutosDesdeAnterior(),
                entity.getDistanciaDesdeAnteriorMetros(),
                entity.getDificultadTramo()
        );
    }
}
