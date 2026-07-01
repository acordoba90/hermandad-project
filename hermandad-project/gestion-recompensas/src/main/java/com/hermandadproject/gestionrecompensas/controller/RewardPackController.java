package com.hermandadproject.gestionrecompensas.controller;

import com.hermandadproject.gestionrecompensas.model.dto.RewardPackResponse;
import com.hermandadproject.gestionrecompensas.service.RewardPackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reward-packs")
public class RewardPackController {

    private final RewardPackService rewardPackService;

    public RewardPackController(RewardPackService rewardPackService) {
        this.rewardPackService = rewardPackService;
    }

    @GetMapping
    public List<RewardPackResponse> findAllActive() {
        return rewardPackService.findAllActive();
    }

    @GetMapping("/{id}")
    public RewardPackResponse findById(@PathVariable UUID id) {
        return rewardPackService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public RewardPackResponse findByCodigo(@PathVariable String codigo) {
        return rewardPackService.findByCodigo(codigo);
    }
}
