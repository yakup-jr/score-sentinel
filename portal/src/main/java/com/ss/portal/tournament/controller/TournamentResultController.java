package com.ss.portal.tournament.controller;

import com.ss.portal.tournament.entity.TournamentResultEntity;
import com.ss.portal.tournament.model.TournamentResultModel;
import com.ss.portal.tournament.service.TournamentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.ss.portal.hateoas.TournamentResultLinkGenerator.linkToTournamentResultById;
import static com.ss.portal.hateoas.TournamentResultLinkGenerator.linkToTournamentResults;

@RestController
@RequestMapping("/tournament-results-management/tournament-results")
public class TournamentResultController {

    private final TournamentResultService tournamentResultService;

    @Autowired
    public TournamentResultController(TournamentResultService tournamentResultService) {
        this.tournamentResultService = tournamentResultService;
    }

    @PostMapping("/one")
    public ResponseEntity<EntityModel<TournamentResultModel>> save(
        @RequestBody TournamentResultEntity tournamentResultEntity) {
        var newTournamentResult = tournamentResultService.save(tournamentResultEntity);

        return ResponseEntity.ok(EntityModel.of(newTournamentResult,
            linkToTournamentResultById(newTournamentResult.getId()).withRel(
                "tournamentResultById")));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<TournamentResultModel>> findById(
        @PathVariable Long id) {
        var tournamentResult = EntityModel.of(tournamentResultService.findById(id),
            linkToTournamentResults().withRel("tournamentResults"));

        return ResponseEntity.ok(tournamentResult);
    }

    @GetMapping("/filter")
    public ResponseEntity<CollectionModel<EntityModel<TournamentResultModel>>> findByFilter(
        @RequestParam(value = "teamId", required = false) Long teamId,
        @RequestParam(value = "tournamentId", required = false) Long tournamentId) {
        var tournamentResults =
            tournamentResultService.findByFilter(teamId, tournamentId);

        var tournamentResultsCollection =
            CollectionModel.of(tournamentResults.stream().map(
                    tournamentResult -> EntityModel.of(tournamentResult,
                        linkToTournamentResultById(tournamentResult.getId()).withRel(
                            "tournamentResultById"))).collect(Collectors.toList()),
                linkToTournamentResults().withRel("allTournamentResults"));

        return ResponseEntity.ok(tournamentResultsCollection);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,
                                        @RequestBody
                                        TournamentResultEntity tournamentResult) {
        tournamentResultService.updateById(id, tournamentResult);

        return ResponseEntity.ok(
            linkToTournamentResultById(tournamentResult.getId()).withRel(
                "tournamentResultById"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        tournamentResultService.deleteById(id);

        return ResponseEntity.ok(linkToTournamentResults().withRel(
            "allTournamentResults"));
    }
}
