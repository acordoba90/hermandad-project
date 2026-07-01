package com.hermandadproject.gestionrecompensas.controller;

import com.hermandadproject.gestionrecompensas.model.dto.OpenRewardPackByCodeRequest;
import com.hermandadproject.gestionrecompensas.model.dto.OpenRewardPackRequest;
import com.hermandadproject.gestionrecompensas.model.dto.RewardPackOpeningResponse;
import com.hermandadproject.gestionrecompensas.service.RewardPackOpeningService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reward-pack-openings")
public class RewardPackOpeningController {

    private final RewardPackOpeningService rewardPackOpeningService;

    public RewardPackOpeningController(RewardPackOpeningService rewardPackOpeningService) {
        this.rewardPackOpeningService = rewardPackOpeningService;
    }

    @PostMapping("/open")
    public ResponseEntity<RewardPackOpeningResponse> openPack(
            @Valid @RequestBody OpenRewardPackRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        RewardPackOpeningResponse opened = rewardPackOpeningService.openPack(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/reward-pack-openings/{id}").buildAndExpand(opened.id()).toUri())
                .body(opened);
    }

    @PostMapping("/open-by-codigo")
    public ResponseEntity<RewardPackOpeningResponse> openPackByCode(
            @Valid @RequestBody OpenRewardPackByCodeRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        RewardPackOpeningResponse opened = rewardPackOpeningService.openPackByCode(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/reward-pack-openings/{id}").buildAndExpand(opened.id()).toUri())
                .body(opened);
    }

    @GetMapping("/hermandad/{idHermandad}")
    public List<RewardPackOpeningResponse> findOpeningsByHermandadId(@PathVariable UUID idHermandad) {
        return rewardPackOpeningService.findOpeningsByHermandadId(idHermandad);
    }
}
