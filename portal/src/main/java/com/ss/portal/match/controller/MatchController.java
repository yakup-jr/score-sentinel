package com.ss.portal.match.controller;

import com.ss.portal.hateoas.MatchLinkGenerator;
import com.ss.portal.match.entity.MatchEntity;
import com.ss.portal.match.model.MatchModel;
import com.ss.portal.match.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/match-management/matches")
public class MatchController {

    private final MatchService matchService;
    private final MatchLinkGenerator matchLinkGenerator;

    @Autowired
    public MatchController(MatchService matchService,
                           MatchLinkGenerator matchLinkGenerator) {
        this.matchService = matchService;
        this.matchLinkGenerator = matchLinkGenerator;
    }

    @PostMapping("/one")
    public ResponseEntity<EntityModel<MatchModel>> save(MatchEntity match) {
        var newMatch = matchService.save(match);

        return ResponseEntity.ok(EntityModel.of(newMatch,
            matchLinkGenerator.linkToMatchById(newMatch.getId()).withRel("matchById")));
    }

    @GetMapping("/id{id}")
    public ResponseEntity<EntityModel<MatchModel>> findById(@PathVariable Long id) {
        var match = matchService.findById(id);

        return ResponseEntity.ok(EntityModel.of(match,
            matchLinkGenerator.linkToMatches().withRel("allMatches")));
    }

    @GetMapping("/filter")
    public ResponseEntity<CollectionModel<EntityModel<MatchModel>>> findByFilter(
        @RequestParam(value = "teamId", required = false) Long teamId,
        @RequestParam(value = "gameId", required = false) Long gameId,
        @RequestParam(value = "matchResultId", required = false) Long matchResultId) {

        var match = CollectionModel.of(matchService.findByFilter(teamId, gameId,
                matchResultId).stream().map(matchModel -> EntityModel.of(matchModel,
                matchLinkGenerator.linkToMatchById(matchModel.getId())
                    .withRel("matchById"))).collect(Collectors.toList()),
            matchLinkGenerator.linkToMatches().withRel("allMatches"));

        return ResponseEntity.ok(match);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,
                                        @RequestBody MatchEntity matchEntity) {
        matchService.updateById(id, matchEntity);
        return ResponseEntity.ok(matchLinkGenerator.linkToMatchById(id).withRel(
            "matchById"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        matchService.deleteById(id);
        return ResponseEntity.ok(
            matchLinkGenerator.linkToMatches().withRel("allMatches"));
    }
}
