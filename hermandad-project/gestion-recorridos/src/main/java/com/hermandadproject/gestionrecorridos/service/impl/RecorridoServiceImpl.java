package com.hermandadproject.gestionrecorridos.service.impl;

import com.hermandadproject.gestionciudad.model.enums.TipoNodoCiudadEnum;
import com.hermandadproject.gestionrecorridos.exception.RecorridoEstadoInvalidoException;
import com.hermandadproject.gestionrecorridos.exception.RecorridoInvalidException;
import com.hermandadproject.gestionrecorridos.exception.RecorridoNoConectadoException;
import com.hermandadproject.gestionrecorridos.exception.RecorridoNotFoundException;
import com.hermandadproject.gestionrecorridos.exception.RecorridoSinCarreraOficialException;
import com.hermandadproject.gestionrecorridos.mapper.RecorridoMapper;
import com.hermandadproject.gestionrecorridos.model.dto.ActualizarRecorridoRequest;
import com.hermandadproject.gestionrecorridos.model.dto.CrearRecorridoRequest;
import com.hermandadproject.gestionrecorridos.model.dto.RecorridoResponse;
import com.hermandadproject.gestionrecorridos.model.dto.ValidarRecorridoResponse;
import com.hermandadproject.gestionrecorridos.model.entity.RecorridoEntity;
import com.hermandadproject.gestionrecorridos.model.entity.RecorridoNodoEntity;
import com.hermandadproject.gestionrecorridos.model.enums.EstadoRecorridoEnum;
import com.hermandadproject.gestionrecorridos.repository.RecorridoNodoRepository;
import com.hermandadproject.gestionrecorridos.repository.RecorridoRepository;
import com.hermandadproject.gestionrecorridos.service.CiudadRecorridoAdapter;
import com.hermandadproject.gestionrecorridos.service.CiudadRecorridoAdapter.CarreraOficialInfo;
import com.hermandadproject.gestionrecorridos.service.CiudadRecorridoAdapter.ConexionCiudadInfo;
import com.hermandadproject.gestionrecorridos.service.CiudadRecorridoAdapter.NodoCiudadInfo;
import com.hermandadproject.gestionrecorridos.service.RecorridoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RecorridoServiceImpl implements RecorridoService {

    private final RecorridoRepository recorridoRepository;
    private final RecorridoNodoRepository recorridoNodoRepository;
    private final RecorridoMapper recorridoMapper;
    private final CiudadRecorridoAdapter ciudadRecorridoAdapter;

    public RecorridoServiceImpl(
            RecorridoRepository recorridoRepository,
            RecorridoNodoRepository recorridoNodoRepository,
            RecorridoMapper recorridoMapper,
            CiudadRecorridoAdapter ciudadRecorridoAdapter
    ) {
        this.recorridoRepository = recorridoRepository;
        this.recorridoNodoRepository = recorridoNodoRepository;
        this.recorridoMapper = recorridoMapper;
        this.ciudadRecorridoAdapter = ciudadRecorridoAdapter;
    }

    @Override
    public RecorridoResponse crear(CrearRecorridoRequest request) {
        CalculoRecorrido calculo = calcularRecorrido(request.idMapaCiudad(), request.idNodoInicio(), request.idsNodos());

        RecorridoEntity entity = new RecorridoEntity();
        entity.setIdHermandad(request.idHermandad());
        entity.setIdCiudad(request.idCiudad());
        entity.setIdMapaCiudad(request.idMapaCiudad());
        entity.setIdIglesiaSede(request.idIglesiaSede());
        entity.setIdNodoInicio(request.idNodoInicio());
        entity.setIdNodoFin(ultimoNodo(request.idsNodos()));
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setEstado(EstadoRecorridoEnum.BORRADOR);
        entity.setDistanciaTotalMetros(calculo.distanciaTotalMetros());
        entity.setMinutosEstimados(calculo.minutosEstimados());
        entity.setDificultadTotal(calculo.dificultadTotal());
        entity.setPasaCarreraOficial(calculo.pasaCarreraOficial());
        entity.setActivo(true);

        RecorridoEntity guardado = recorridoRepository.save(entity);
        List<RecorridoNodoEntity> nodos = guardarNodos(guardado, calculo);
        return recorridoMapper.toResponse(guardado, nodos);
    }

    @Override
    @Transactional(readOnly = true)
    public RecorridoResponse buscarPorId(UUID id) {
        RecorridoEntity entity = buscarEntity(id);
        return recorridoMapper.toResponse(entity, nodosDe(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecorridoResponse> listarPorHermandad(UUID idHermandad) {
        return recorridoRepository.findByIdHermandadAndActivoTrue(idHermandad)
                .stream()
                .map(entity -> recorridoMapper.toResponse(entity, nodosDe(entity)))
                .toList();
    }

    @Override
    public RecorridoResponse actualizar(UUID id, ActualizarRecorridoRequest request) {
        RecorridoEntity entity = buscarEntity(id);
        CalculoRecorrido calculo = calcularRecorrido(entity.getIdMapaCiudad(), entity.getIdNodoInicio(), request.idsNodos());

        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setIdNodoFin(ultimoNodo(request.idsNodos()));
        entity.setEstado(EstadoRecorridoEnum.BORRADOR);
        entity.setActivo(true);
        aplicarCalculo(entity, calculo);

        RecorridoEntity guardado = recorridoRepository.save(entity);
        recorridoNodoRepository.deleteByRecorridoId(guardado.getId());
        List<RecorridoNodoEntity> nodos = guardarNodos(guardado, calculo);
        return recorridoMapper.toResponse(guardado, nodos);
    }

    @Override
    public ValidarRecorridoResponse validar(UUID id) {
        RecorridoEntity entity = buscarEntity(id);
        List<UUID> idsNodos = nodosDe(entity).stream().map(RecorridoNodoEntity::getIdNodoCiudad).toList();

        try {
            CalculoRecorrido calculo = calcularRecorrido(entity.getIdMapaCiudad(), entity.getIdNodoInicio(), idsNodos);
            aplicarCalculo(entity, calculo);
            if (entity.getEstado() != EstadoRecorridoEnum.ACTIVO) {
                entity.setEstado(EstadoRecorridoEnum.VALIDADO);
                entity.setActivo(true);
            }
            RecorridoEntity guardado = recorridoRepository.save(entity);
            recorridoNodoRepository.deleteByRecorridoId(guardado.getId());
            guardarNodos(guardado, calculo);
            return new ValidarRecorridoResponse(
                    true,
                    List.of("Recorrido valido"),
                    calculo.distanciaTotalMetros(),
                    calculo.minutosEstimados(),
                    calculo.dificultadTotal(),
                    calculo.pasaCarreraOficial()
            );
        } catch (RecorridoInvalidException | RecorridoNoConectadoException | RecorridoSinCarreraOficialException ex) {
            return new ValidarRecorridoResponse(
                    false,
                    List.of(ex.getMessage()),
                    entity.getDistanciaTotalMetros(),
                    entity.getMinutosEstimados(),
                    entity.getDificultadTotal(),
                    entity.getPasaCarreraOficial()
            );
        }
    }

    @Override
    public RecorridoResponse activar(UUID id) {
        RecorridoEntity entity = buscarEntity(id);
        if (entity.getEstado() != EstadoRecorridoEnum.VALIDADO) {
            throw new RecorridoEstadoInvalidoException("Solo se puede activar un recorrido validado");
        }

        recorridoRepository.findByIdHermandadAndEstadoAndActivoTrue(entity.getIdHermandad(), EstadoRecorridoEnum.ACTIVO)
                .forEach(activo -> {
                    activo.setEstado(EstadoRecorridoEnum.INACTIVO);
                    activo.setActivo(false);
                    recorridoRepository.save(activo);
                });

        entity.setEstado(EstadoRecorridoEnum.ACTIVO);
        entity.setActivo(true);
        RecorridoEntity guardado = recorridoRepository.save(entity);
        return recorridoMapper.toResponse(guardado, nodosDe(guardado));
    }

    @Override
    public void desactivar(UUID id) {
        RecorridoEntity entity = buscarEntity(id);
        entity.setEstado(EstadoRecorridoEnum.INACTIVO);
        entity.setActivo(false);
        recorridoRepository.save(entity);
    }

    @Override
    public void eliminar(UUID id) {
        desactivar(id);
    }

    private RecorridoEntity buscarEntity(UUID id) {
        return recorridoRepository.findById(id)
                .orElseThrow(() -> new RecorridoNotFoundException("Recorrido no encontrado"));
    }

    private List<RecorridoNodoEntity> nodosDe(RecorridoEntity recorrido) {
        return recorridoNodoRepository.findByRecorridoIdOrderByOrdenAsc(recorrido.getId());
    }

    private void aplicarCalculo(RecorridoEntity entity, CalculoRecorrido calculo) {
        entity.setDistanciaTotalMetros(calculo.distanciaTotalMetros());
        entity.setMinutosEstimados(calculo.minutosEstimados());
        entity.setDificultadTotal(calculo.dificultadTotal());
        entity.setPasaCarreraOficial(calculo.pasaCarreraOficial());
    }

    private List<RecorridoNodoEntity> guardarNodos(RecorridoEntity recorrido, CalculoRecorrido calculo) {
        List<RecorridoNodoEntity> nodos = calculo.nodos().stream()
                .map(nodo -> toEntity(recorrido, nodo))
                .toList();
        return recorridoNodoRepository.saveAll(nodos);
    }

    private RecorridoNodoEntity toEntity(RecorridoEntity recorrido, NodoCalculado nodo) {
        RecorridoNodoEntity entity = new RecorridoNodoEntity();
        entity.setRecorrido(recorrido);
        entity.setIdNodoCiudad(nodo.info().id());
        entity.setCodigoNodo(nodo.info().codigo());
        entity.setNombreNodo(nodo.info().nombre());
        entity.setOrden(nodo.orden());
        entity.setMinutosDesdeAnterior(nodo.minutosDesdeAnterior());
        entity.setDistanciaDesdeAnteriorMetros(nodo.distanciaDesdeAnteriorMetros());
        entity.setDificultadTramo(nodo.dificultadTramo());
        return entity;
    }

    private CalculoRecorrido calcularRecorrido(UUID idMapaCiudad, UUID idNodoInicio, List<UUID> idsNodos) {
        validarEstructuraBasica(idNodoInicio, idsNodos);

        CarreraOficialInfo carreraOficial = ciudadRecorridoAdapter.buscarCarreraOficialPorMapa(idMapaCiudad)
                .orElseThrow(() -> new RecorridoSinCarreraOficialException("No existe carrera oficial activa para el mapa indicado"));

        List<NodoCalculado> nodos = new ArrayList<>();
        int distanciaTotal = 0;
        int minutosTotal = 0;
        int dificultadTotal = 0;
        int posicionEntrada = -1;
        int posicionSalida = -1;

        for (int i = 0; i < idsNodos.size(); i++) {
            UUID idNodoActual = idsNodos.get(i);
            NodoCiudadInfo nodoActual = ciudadRecorridoAdapter.buscarNodo(idNodoActual)
                    .orElseThrow(() -> new RecorridoInvalidException("Nodo de ciudad no encontrado: " + idNodoActual));

            if (!nodoActual.idMapaCiudad().equals(idMapaCiudad)) {
                throw new RecorridoInvalidException("Todos los nodos deben pertenecer al mapa indicado");
            }

            int distanciaTramo = 0;
            int minutosTramo = 0;
            int dificultadTramo = 0;
            if (i > 0) {
                UUID idNodoAnterior = idsNodos.get(i - 1);
                ConexionCiudadInfo conexion = ciudadRecorridoAdapter.buscarConexion(idMapaCiudad, idNodoAnterior, idNodoActual)
                        .orElseThrow(() -> new RecorridoNoConectadoException("No existe conexion entre nodos consecutivos"));
                distanciaTramo = conexion.distanciaMetros();
                minutosTramo = conexion.minutosEstimados();
                dificultadTramo = conexion.dificultad();
                distanciaTotal += distanciaTramo;
                minutosTotal += minutosTramo;
                dificultadTotal += dificultadTramo;
            }

            if (idNodoActual.equals(carreraOficial.idNodoEntrada()) || nodoActual.tipo() == TipoNodoCiudadEnum.CARRERA_OFICIAL_ENTRADA) {
                posicionEntrada = i;
            }
            if (idNodoActual.equals(carreraOficial.idNodoSalida()) || nodoActual.tipo() == TipoNodoCiudadEnum.CARRERA_OFICIAL_SALIDA) {
                posicionSalida = i;
            }

            nodos.add(new NodoCalculado(nodoActual, i + 1, minutosTramo, distanciaTramo, dificultadTramo));
        }

        if (posicionEntrada < 0 || posicionSalida < 0) {
            throw new RecorridoSinCarreraOficialException("El recorrido debe pasar por entrada y salida de carrera oficial");
        }
        if (posicionEntrada >= posicionSalida) {
            throw new RecorridoSinCarreraOficialException("La entrada de carrera oficial debe aparecer antes que la salida");
        }

        return new CalculoRecorrido(nodos, distanciaTotal, minutosTotal, dificultadTotal, true);
    }

    private void validarEstructuraBasica(UUID idNodoInicio, List<UUID> idsNodos) {
        if (idsNodos == null || idsNodos.size() < 3) {
            throw new RecorridoInvalidException("El recorrido debe tener al menos 3 nodos");
        }
        if (!idsNodos.get(0).equals(idNodoInicio)) {
            throw new RecorridoInvalidException("El primer nodo debe coincidir con el nodo de inicio");
        }
        if (!ultimoNodo(idsNodos).equals(idNodoInicio)) {
            throw new RecorridoInvalidException("El ultimo nodo debe coincidir con el nodo de inicio para cerrar el recorrido");
        }
    }

    private UUID ultimoNodo(List<UUID> idsNodos) {
        return idsNodos.get(idsNodos.size() - 1);
    }

    private record NodoCalculado(
            NodoCiudadInfo info,
            Integer orden,
            Integer minutosDesdeAnterior,
            Integer distanciaDesdeAnteriorMetros,
            Integer dificultadTramo
    ) {
    }

    private record CalculoRecorrido(
            List<NodoCalculado> nodos,
            Integer distanciaTotalMetros,
            Integer minutosEstimados,
            Integer dificultadTotal,
            Boolean pasaCarreraOficial
    ) {
    }
}
