package com.ss.portal.round.controller;

import com.ss.portal.hateoas.RoundResultLinkGenerator;
import com.ss.portal.round.entity.RoundResultEntity;
import com.ss.portal.round.model.RoundResultModel;
import com.ss.portal.round.service.RoundResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/round-result-management/round-results")
public class RoundResultController {

    private final RoundResultService roundResultService;
    private final RoundResultLinkGenerator roundResultLinkGenerator;

    @Autowired
    public RoundResultController(RoundResultService roundResultService,
                                 RoundResultLinkGenerator roundResultLinkGenerator) {
        this.roundResultService = roundResultService;
        this.roundResultLinkGenerator = roundResultLinkGenerator;
    }

    @PostMapping("/one")
    public ResponseEntity<EntityModel<RoundResultModel>> save(@RequestBody
                                                              RoundResultEntity roundResultEntity) {
        var newRoundResult = roundResultService.save(roundResultEntity);

        return ResponseEntity.ok(EntityModel.of(newRoundResult,
            RoundResultLinkGenerator.linkToRoundResults().withRel("allRoundResults")));
    }

    //todo: what return linkTo...ByFilter
    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<RoundResultModel>> findById(@PathVariable Long id) {
        var roundResult = roundResultService.findById(id);

        return ResponseEntity.ok(EntityModel.of(roundResult,
            RoundResultLinkGenerator.linkToRoundResults().withRel("allRoundResults")));
    }

    @GetMapping("/filter")
    public ResponseEntity<CollectionModel<EntityModel<RoundResultModel>>> findByFilter(
        @RequestParam(value = "teamId", required = false) Long teamId,
        @RequestParam(value = "roundId", required = false) Long roundId) {
        var roundResults = roundResultService.findByFilter(teamId, roundId);

        var roundResultsCollection =
            CollectionModel.of(roundResults.stream().map(round -> EntityModel.of(round,
                    RoundResultLinkGenerator.linkToRoundResults()
                        .withRel("allRoundResults"))).collect(Collectors.toList()),
                RoundResultLinkGenerator.linkToRoundResults().withRel("allRoundResults"));

        return ResponseEntity.ok(roundResultsCollection);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,
                                        @RequestBody RoundResultEntity updateEntity) {
        roundResultService.updateById(id, updateEntity);

        return ResponseEntity.ok(RoundResultLinkGenerator.linkToRoundResultById(id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        roundResultService.deleteById(id);

        return ResponseEntity.ok(
            RoundResultLinkGenerator.linkToRoundResults().withRel("allRoundResults"));
    }

}
