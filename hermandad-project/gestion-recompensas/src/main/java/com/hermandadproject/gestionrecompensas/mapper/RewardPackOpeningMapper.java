package com.hermandadproject.gestionrecompensas.mapper;

import com.hermandadproject.gestionrecompensas.model.dto.RewardPackOpeningResponse;
import com.hermandadproject.gestionrecompensas.model.entity.SobreRecompensaEntity;
import com.hermandadproject.gestionrecompensas.model.entity.AperturaSobreRecompensaEntity;
import com.hermandadproject.gestionrecompensas.model.entity.RecompensaSobreEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardPackOpeningMapper {

    private final RewardPackRewardMapper rewardPackRewardMapper;

    public RewardPackOpeningMapper(RewardPackRewardMapper rewardPackRewardMapper) {
        this.rewardPackRewardMapper = rewardPackRewardMapper;
    }

    public RewardPackOpeningResponse toResponse(AperturaSobreRecompensaEntity apertura, List<RecompensaSobreEntity> recompensas) {
        SobreRecompensaEntity sobreRecompensa = apertura.getSobreRecompensa();
        return new RewardPackOpeningResponse(
                apertura.getId(),
                apertura.getIdHermandad(),
                sobreRecompensa.getId(),
                sobreRecompensa.getCodigo(),
                sobreRecompensa.getNombre(),
                apertura.getFechaApertura(),
                recompensas.stream().map(rewardPackRewardMapper::toResponse).toList()
        );
    }
}
