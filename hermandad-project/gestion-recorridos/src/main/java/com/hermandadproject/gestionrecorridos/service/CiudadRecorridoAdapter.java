package com.hermandadproject.gestionrecorridos.service;

import com.hermandadproject.gestionciudad.model.entity.CarreraOficialEntity;
import com.hermandadproject.gestionciudad.model.entity.ConexionCiudadEntity;
import com.hermandadproject.gestionciudad.model.entity.NodoCiudadEntity;
import com.hermandadproject.gestionciudad.model.enums.TipoNodoCiudadEnum;
import com.hermandadproject.gestionciudad.repository.CarreraOficialRepository;
import com.hermandadproject.gestionciudad.repository.ConexionCiudadRepository;
import com.hermandadproject.gestionciudad.repository.NodoCiudadRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CiudadRecorridoAdapter {

    private final NodoCiudadRepository nodoCiudadRepository;
    private final ConexionCiudadRepository conexionCiudadRepository;
    private final CarreraOficialRepository carreraOficialRepository;

    public CiudadRecorridoAdapter(
            NodoCiudadRepository nodoCiudadRepository,
            ConexionCiudadRepository conexionCiudadRepository,
            CarreraOficialRepository carreraOficialRepository
    ) {
        this.nodoCiudadRepository = nodoCiudadRepository;
        this.conexionCiudadRepository = conexionCiudadRepository;
        this.carreraOficialRepository = carreraOficialRepository;
    }

    public Optional<NodoCiudadInfo> buscarNodo(UUID idNodoCiudad) {
        return nodoCiudadRepository.findById(idNodoCiudad)
                .filter(nodo -> Boolean.TRUE.equals(nodo.getActivo()))
                .map(this::toNodoInfo);
    }

    public Optional<ConexionCiudadInfo> buscarConexion(UUID idMapaCiudad, UUID idNodoOrigen, UUID idNodoDestino) {
        return conexionCiudadRepository.findByNodoOrigenIdOrNodoDestinoId(idNodoOrigen, idNodoOrigen)
                .stream()
                .filter(conexion -> Boolean.TRUE.equals(conexion.getActiva()))
                .filter(conexion -> conexion.getMapaCiudad().getId().equals(idMapaCiudad))
                .filter(conexion -> conecta(conexion, idNodoOrigen, idNodoDestino))
                .findFirst()
                .map(this::toConexionInfo);
    }

    public Optional<CarreraOficialInfo> buscarCarreraOficialPorMapa(UUID idMapaCiudad) {
        return carreraOficialRepository.findByMapaCiudadIdAndActivaTrue(idMapaCiudad)
                .map(this::toCarreraOficialInfo);
    }

    private boolean conecta(ConexionCiudadEntity conexion, UUID idNodoOrigen, UUID idNodoDestino) {
        UUID origen = conexion.getNodoOrigen().getId();
        UUID destino = conexion.getNodoDestino().getId();
        return origen.equals(idNodoOrigen) && destino.equals(idNodoDestino)
                || origen.equals(idNodoDestino) && destino.equals(idNodoOrigen);
    }

    private NodoCiudadInfo toNodoInfo(NodoCiudadEntity entity) {
        return new NodoCiudadInfo(
                entity.getId(),
                entity.getMapaCiudad().getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getTipo()
        );
    }

    private ConexionCiudadInfo toConexionInfo(ConexionCiudadEntity entity) {
        return new ConexionCiudadInfo(
                entity.getDistanciaMetros(),
                entity.getMinutosEstimados(),
                entity.getDificultad()
        );
    }

    private CarreraOficialInfo toCarreraOficialInfo(CarreraOficialEntity entity) {
        return new CarreraOficialInfo(
                entity.getNodoEntrada().getId(),
                entity.getNodoSalida().getId()
        );
    }

    public record NodoCiudadInfo(
            UUID id,
            UUID idMapaCiudad,
            String codigo,
            String nombre,
            TipoNodoCiudadEnum tipo
    ) {
    }

    public record ConexionCiudadInfo(
            Integer distanciaMetros,
            Integer minutosEstimados,
            Integer dificultad
    ) {
    }

    public record CarreraOficialInfo(
            UUID idNodoEntrada,
            UUID idNodoSalida
    ) {
    }
}
