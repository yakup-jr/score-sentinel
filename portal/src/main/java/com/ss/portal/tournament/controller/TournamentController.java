package com.ss.portal.tournament.controller;


import com.ss.portal.hateoas.TournamentLinkGenerator;
import com.ss.portal.tournament.entity.TournamentEntity;
import com.ss.portal.tournament.model.TournamentModel;
import com.ss.portal.tournament.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.ss.portal.hateoas.TournamentLinkGenerator.linkToTournamentById;
import static com.ss.portal.hateoas.TournamentLinkGenerator.linkToTournaments;

@RestController
@RequestMapping("/tournaments-management/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    private final TournamentLinkGenerator tournamentLinkGenerator;

    @Autowired
    public TournamentController(TournamentService tournamentService,
                                TournamentLinkGenerator tournamentLinkGenerator) {
        this.tournamentService = tournamentService;
        this.tournamentLinkGenerator = tournamentLinkGenerator;
    }

    @PostMapping("/one")
    public ResponseEntity<EntityModel<TournamentModel>> save(
        @RequestBody TournamentEntity tournament) {
        var newTournament = tournamentService.save(tournament);

        return ResponseEntity.ok(EntityModel.of(newTournament,
            linkToTournamentById(newTournament.getId()).withRel("tournamentById")));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<TournamentModel>> findById(@PathVariable Long id) {
        var tournament = tournamentService.findById(id);

        return ResponseEntity.ok(EntityModel.of(tournament,
            linkToTournaments().withRel("allTournaments")));
    }

    @GetMapping("/filter")
    public ResponseEntity<CollectionModel<EntityModel<TournamentModel>>> findByFilter(
        @RequestParam(value = "teamId", required = false) Long teamId,
        @RequestParam(value = "roundId", required = false) Long roundId,
        @RequestParam(value = "gameId", required = false) Long gameId,
        @RequestParam(value = "tournamentResultId", required = false)
        Long tournamentResultId) {
        var tournaments =
            tournamentService.findByFilter(teamId, roundId, gameId, tournamentResultId);

        var tournamentsCollection =
            CollectionModel.of(
                tournaments.stream().map(tournament -> EntityModel.of(tournament,
                    linkToTournamentById(tournament.getId()).withRel(
                        "tournamentById"))).collect(Collectors.toList()),
                linkToTournaments().withRel("allTournaments"));

        return ResponseEntity.ok(tournamentsCollection);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,
                                        @RequestBody TournamentEntity tournament) {
        tournamentService.updateById(id, tournament);

        return ResponseEntity.ok(linkToTournamentById(id).withRel("tournamentById"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        tournamentService.deleteById(id);

        return ResponseEntity.ok(linkToTournaments());
    }
}
