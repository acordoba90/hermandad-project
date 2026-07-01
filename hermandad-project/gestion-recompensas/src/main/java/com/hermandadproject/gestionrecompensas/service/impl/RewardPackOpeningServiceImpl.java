package com.hermandadproject.gestionrecompensas.service.impl;

import com.hermandadproject.gestioninventario.model.dto.InventoryItemCreateRequest;
import com.hermandadproject.gestioninventario.model.enums.InventoryItemTypeEnum;
import com.hermandadproject.gestioninventario.service.InventoryService;
import com.hermandadproject.gestionrecompensas.exception.EmptyRewardPackPoolException;
import com.hermandadproject.gestionrecompensas.exception.RewardPackInactiveException;
import com.hermandadproject.gestionrecompensas.exception.RewardPackNotFoundException;
import com.hermandadproject.gestionrecompensas.mapper.RewardPackOpeningMapper;
import com.hermandadproject.gestionrecompensas.mapper.RewardPackRewardMapper;
import com.hermandadproject.gestionrecompensas.model.dto.OpenRewardPackByCodeRequest;
import com.hermandadproject.gestionrecompensas.model.dto.OpenRewardPackRequest;
import com.hermandadproject.gestionrecompensas.model.dto.RewardPackOpeningResponse;
import com.hermandadproject.gestionrecompensas.model.entity.SobreRecompensaEntity;
import com.hermandadproject.gestionrecompensas.model.entity.AperturaSobreRecompensaEntity;
import com.hermandadproject.gestionrecompensas.model.entity.ElementoPoolSobreRecompensaEntity;
import com.hermandadproject.gestionrecompensas.model.entity.RecompensaSobreEntity;
import com.hermandadproject.gestionrecompensas.model.enums.RewardItemTypeEnum;
import com.hermandadproject.gestionrecompensas.repository.RewardPackOpeningRepository;
import com.hermandadproject.gestionrecompensas.repository.RewardPackPoolItemRepository;
import com.hermandadproject.gestionrecompensas.repository.RewardPackRepository;
import com.hermandadproject.gestionrecompensas.repository.RewardPackRewardRepository;
import com.hermandadproject.gestionrecompensas.service.RewardPackOpeningService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class RewardPackOpeningServiceImpl implements RewardPackOpeningService {

    private final RewardPackRepository rewardPackRepository;
    private final RewardPackPoolItemRepository rewardPackPoolItemRepository;
    private final RewardPackOpeningRepository rewardPackOpeningRepository;
    private final RewardPackRewardRepository rewardPackRewardRepository;
    private final RewardPackRewardMapper rewardPackRewardMapper;
    private final RewardPackOpeningMapper rewardPackOpeningMapper;
    private final InventoryService inventoryService;

    public RewardPackOpeningServiceImpl(
            RewardPackRepository rewardPackRepository,
            RewardPackPoolItemRepository rewardPackPoolItemRepository,
            RewardPackOpeningRepository rewardPackOpeningRepository,
            RewardPackRewardRepository rewardPackRewardRepository,
            RewardPackRewardMapper rewardPackRewardMapper,
            RewardPackOpeningMapper rewardPackOpeningMapper,
            InventoryService inventoryService
    ) {
        this.rewardPackRepository = rewardPackRepository;
        this.rewardPackPoolItemRepository = rewardPackPoolItemRepository;
        this.rewardPackOpeningRepository = rewardPackOpeningRepository;
        this.rewardPackRewardRepository = rewardPackRewardRepository;
        this.rewardPackRewardMapper = rewardPackRewardMapper;
        this.rewardPackOpeningMapper = rewardPackOpeningMapper;
        this.inventoryService = inventoryService;
    }

    @Override
    public RewardPackOpeningResponse openPack(OpenRewardPackRequest request) {
        SobreRecompensaEntity sobreRecompensa = rewardPackRepository.findById(request.idSobreRecompensa())
                .orElseThrow(() -> new RewardPackNotFoundException("Sobre de recompensas no encontrado"));
        return openPack(request.idHermandad(), sobreRecompensa);
    }

    @Override
    public RewardPackOpeningResponse openPackByCode(OpenRewardPackByCodeRequest request) {
        SobreRecompensaEntity sobreRecompensa = rewardPackRepository.findByCodigo(request.codigoSobreRecompensa())
                .orElseThrow(() -> new RewardPackNotFoundException("Sobre de recompensas no encontrado"));
        return openPack(request.idHermandad(), sobreRecompensa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RewardPackOpeningResponse> findOpeningsByHermandadId(UUID idHermandad) {
        return rewardPackOpeningRepository.findByIdHermandad(idHermandad)
                .stream()
                .map(apertura -> rewardPackOpeningMapper.toResponse(
                        apertura,
                        rewardPackRewardRepository.findByAperturaId(apertura.getId())
                ))
                .toList();
    }

    private RewardPackOpeningResponse openPack(UUID idHermandad, SobreRecompensaEntity sobreRecompensa) {
        if (!Boolean.TRUE.equals(sobreRecompensa.getActivo())) {
            throw new RewardPackInactiveException("El sobre de recompensas no estÃ¡ activo");
        }

        List<ElementoPoolSobreRecompensaEntity> poolItems = rewardPackPoolItemRepository
                .findBySobreRecompensaIdAndActivoTrue(sobreRecompensa.getId())
                .stream()
                .filter(item -> item.getPeso() != null && item.getPeso() > 0)
                .toList();
        if (poolItems.isEmpty()) {
            throw new EmptyRewardPackPoolException("El sobre no tiene recompensas disponibles");
        }

        AperturaSobreRecompensaEntity apertura = new AperturaSobreRecompensaEntity();
        apertura.setIdHermandad(idHermandad);
        apertura.setSobreRecompensa(sobreRecompensa);
        apertura.setFechaApertura(Instant.now());
        AperturaSobreRecompensaEntity savedOpening = rewardPackOpeningRepository.save(apertura);

        List<RecompensaSobreEntity> recompensas = new ArrayList<>();
        int cantidadRecompensas = sobreRecompensa.getCantidadRecompensas() == null ? 0 : sobreRecompensa.getCantidadRecompensas();
        for (int i = 0; i < cantidadRecompensas; i++) {
            ElementoPoolSobreRecompensaEntity selected = selectWeighted(poolItems);
            RecompensaSobreEntity reward = rewardPackRewardMapper.toEntity(selected);
            reward.setApertura(savedOpening);
            recompensas.add(reward);
        }

        List<RecompensaSobreEntity> savedRewards = rewardPackRewardRepository.saveAll(recompensas);
        savedRewards.forEach(reward -> addRewardToInventoryIfApplicable(idHermandad, reward));

        return rewardPackOpeningMapper.toResponse(savedOpening, savedRewards);
    }

    private ElementoPoolSobreRecompensaEntity selectWeighted(List<ElementoPoolSobreRecompensaEntity> poolItems) {
        int totalWeight = poolItems.stream().mapToInt(ElementoPoolSobreRecompensaEntity::getPeso).sum();
        int roll = ThreadLocalRandom.current().nextInt(totalWeight);
        int cumulative = 0;

        for (ElementoPoolSobreRecompensaEntity poolItem : poolItems) {
            cumulative += poolItem.getPeso();
            if (roll < cumulative) {
                return poolItem;
            }
        }

        return poolItems.get(poolItems.size() - 1);
    }

    private void addRewardToInventoryIfApplicable(UUID idHermandad, RecompensaSobreEntity reward) {
        InventoryItemTypeEnum inventoryItemType = toInventoryItemType(reward.getTipoElemento());
        if (inventoryItemType == null) {
            // TODO Integrar MONEY, PRESTIGE y DEVOTION con gestion-economia/gestion-hermandades en una fase posterior.
            return;
        }

        inventoryService.addItem(new InventoryItemCreateRequest(
                idHermandad,
                inventoryItemType,
                reward.getIdElemento(),
                reward.getCodigoElemento(),
                reward.getCantidad()
        ));
    }

    private InventoryItemTypeEnum toInventoryItemType(RewardItemTypeEnum tipoElemento) {
        return switch (tipoElemento) {
            case PASO_TEMPLATE -> InventoryItemTypeEnum.PASO_TEMPLATE;
            case PASO_FIGURE -> InventoryItemTypeEnum.PASO_FIGURE;
            case PERSONAJE -> InventoryItemTypeEnum.PERSONAJE;
            case ASSET -> InventoryItemTypeEnum.ASSET;
            case RESOURCE -> InventoryItemTypeEnum.RESOURCE;
            case MONEY, PRESTIGE, DEVOTION -> null;
        };
    }
}
