package com.hermandadproject.gestionpasos.service.impl;

import com.hermandadproject.gestionpasos.exception.InvalidPasoSlotException;
import com.hermandadproject.gestionpasos.exception.PasoFigureNotFoundException;
import com.hermandadproject.gestionpasos.exception.PasoSlotNotFoundException;
import com.hermandadproject.gestionpasos.exception.PasoTemplateNotFoundException;
import com.hermandadproject.gestionpasos.exception.SlotAlreadyOccupiedException;
import com.hermandadproject.gestionpasos.mapper.PlacedFigureMapper;
import com.hermandadproject.gestionpasos.model.dto.PlacedFigureRequest;
import com.hermandadproject.gestionpasos.model.dto.PlacedFigureResponse;
import com.hermandadproject.gestionpasos.model.entity.FiguraPasoEntity;
import com.hermandadproject.gestionpasos.model.entity.HuecoPasoEntity;
import com.hermandadproject.gestionpasos.model.entity.PlantillaPasoEntity;
import com.hermandadproject.gestionpasos.model.entity.FiguraColocadaEntity;
import com.hermandadproject.gestionpasos.repository.PasoFigureRepository;
import com.hermandadproject.gestionpasos.repository.PasoSlotRepository;
import com.hermandadproject.gestionpasos.repository.PasoTemplateRepository;
import com.hermandadproject.gestionpasos.repository.PlacedFigureRepository;
import com.hermandadproject.gestionpasos.service.PasoCompositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PasoCompositionServiceImpl implements PasoCompositionService {

    private final PlacedFigureRepository placedFigureRepository;
    private final PasoTemplateRepository pasoTemplateRepository;
    private final PasoSlotRepository pasoSlotRepository;
    private final PasoFigureRepository pasoFigureRepository;
    private final PlacedFigureMapper placedFigureMapper;

    public PasoCompositionServiceImpl(
            PlacedFigureRepository placedFigureRepository,
            PasoTemplateRepository pasoTemplateRepository,
            PasoSlotRepository pasoSlotRepository,
            PasoFigureRepository pasoFigureRepository,
            PlacedFigureMapper placedFigureMapper
    ) {
        this.placedFigureRepository = placedFigureRepository;
        this.pasoTemplateRepository = pasoTemplateRepository;
        this.pasoSlotRepository = pasoSlotRepository;
        this.pasoFigureRepository = pasoFigureRepository;
        this.placedFigureMapper = placedFigureMapper;
    }

    @Override
    public PlacedFigureResponse placeFigure(PlacedFigureRequest request) {
        PlantillaPasoEntity plantillaPaso = pasoTemplateRepository.findById(request.idPlantillaPaso())
                .orElseThrow(() -> new PasoTemplateNotFoundException("Paso base no encontrado"));
        HuecoPasoEntity huecoPaso = pasoSlotRepository.findById(request.idHuecoPaso())
                .orElseThrow(() -> new PasoSlotNotFoundException("Slot de paso no encontrado"));
        FiguraPasoEntity figuraPaso = pasoFigureRepository.findById(request.idFiguraPaso())
                .orElseThrow(() -> new PasoFigureNotFoundException("Figura de paso no encontrada"));

        if (!huecoPaso.getPlantillaPaso().getId().equals(plantillaPaso.getId())) {
            throw new InvalidPasoSlotException("El slot no pertenece al paso base indicado");
        }

        boolean occupied = placedFigureRepository.existsByIdHermandadAndPlantillaPasoIdAndHuecoPasoId(
                request.idHermandad(),
                request.idPlantillaPaso(),
                request.idHuecoPaso()
        );
        if (occupied) {
            throw new SlotAlreadyOccupiedException("El slot ya tiene una figura colocada para esa hermandad y paso");
        }

        FiguraColocadaEntity entity = new FiguraColocadaEntity();
        entity.setIdHermandad(request.idHermandad());
        entity.setPlantillaPaso(plantillaPaso);
        entity.setHuecoPaso(huecoPaso);
        entity.setFiguraPaso(figuraPaso);
        entity.setDesplazamientoX(request.desplazamientoX() == null ? 0 : request.desplazamientoX());
        entity.setDesplazamientoY(request.desplazamientoY() == null ? 0 : request.desplazamientoY());
        entity.setEscala(request.escala() == null ? huecoPaso.getEscalaPorDefecto() : request.escala());
        entity.setRotacion(request.rotacion() == null ? huecoPaso.getRotacionPorDefecto() : request.rotacion());

        FiguraColocadaEntity saved = placedFigureRepository.save(entity);
        return placedFigureMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlacedFigureResponse> findComposition(UUID idHermandad, UUID idPlantillaPaso) {
        if (!pasoTemplateRepository.existsById(idPlantillaPaso)) {
            throw new PasoTemplateNotFoundException("Paso base no encontrado");
        }

        return placedFigureRepository.findByIdHermandadAndPlantillaPasoId(idHermandad, idPlantillaPaso)
                .stream()
                .map(placedFigureMapper::toResponse)
                .toList();
    }

    @Override
    public void removePlacedFigure(UUID placedFigureId) {
        FiguraColocadaEntity entity = placedFigureRepository.findById(placedFigureId)
                .orElseThrow(() -> new PasoFigureNotFoundException("Figura colocada no encontrada"));
        placedFigureRepository.delete(entity);
    }
}
