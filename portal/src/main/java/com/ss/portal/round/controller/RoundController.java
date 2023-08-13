package com.ss.portal.round.controller;

import com.ss.portal.hateoas.RoundLinkGeneration;
import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.round.model.RoundModel;
import com.ss.portal.round.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/round-management/rounds")
public class RoundController {

    private RoundService roundService;
    private RoundLinkGeneration roundLinkGeneration;

    @Autowired
    public RoundController(RoundService roundService,
                           RoundLinkGeneration roundLinkGeneration) {
        this.roundService = roundService;
        this.roundLinkGeneration = roundLinkGeneration;
    }

    @PostMapping("/one")
    public ResponseEntity<EntityModel<RoundModel>> save(@RequestBody RoundEntity round) {
        var newRound = roundService.save(round);

        return ResponseEntity.ok(EntityModel.of(newRound,
            roundLinkGeneration.linkToRoundById(newRound.getId()).withRel("roundById")));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<RoundModel>> findById(@PathVariable Long id) {
        var round = roundService.findById(id);

        return ResponseEntity.ok(EntityModel.of(round,
            roundLinkGeneration.linkToRounds().withRel("allRounds")));
    }

    @GetMapping("/filter")
    public ResponseEntity<CollectionModel<EntityModel<RoundModel>>> findByFilter(
        @RequestParam(value = "matchId", required = false) Long matchId,
        @RequestParam(value = "teamId", required = false) Long teamId,
        @RequestParam(value = "gameId", required = false) Long gameId,
        @RequestParam(value = "roundResultId", required = false) Long roundResultId) {
        var roundModels = roundService.findByFilter(matchId, teamId, gameId,
            roundResultId);

        var roundCollection =
            CollectionModel.of(roundModels.stream().map(round -> EntityModel.of(round,
                    roundLinkGeneration.linkToRoundById(round.getId()).withRel("roundById")))
                .collect(
                    Collectors.toList()), roundLinkGeneration.linkToRounds().withRel(
                "allRounds"));

        return ResponseEntity.ok(roundCollection);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,
                                        @RequestBody RoundEntity round) {
        roundService.updateById(id, round);

        return ResponseEntity.ok(roundLinkGeneration.linkToRoundById(id).withRel(
            "roundById"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        roundService.deleteById(id);

        return ResponseEntity.ok(roundLinkGeneration.linkToRounds().withRel("allRounds"));
    }
}
