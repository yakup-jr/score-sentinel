package com.ss.portal.match.controller;

import com.ss.portal.hateoas.MatchResultLinkGenerator;
import com.ss.portal.match.entity.MatchResultEntity;
import com.ss.portal.match.model.MatchResultModel;
import com.ss.portal.match.service.MatchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/match-result-management/match-results")
public class MatchResultController {
    private final MatchResultService matchResultService;

    private final MatchResultLinkGenerator matchResultLinkGenerator;

    @Autowired
    public MatchResultController(MatchResultService matchResultService,
                                 MatchResultLinkGenerator matchResultLinkGenerator) {
        this.matchResultService = matchResultService;
        this.matchResultLinkGenerator = matchResultLinkGenerator;
    }


    // todo: think how to define id for new instance
    @PostMapping("/one")
    public EntityModel<MatchResultModel> save(
        @RequestBody MatchResultEntity matchResultCreate) {
        return EntityModel.of(matchResultService.save(matchResultCreate),
            matchResultLinkGenerator.linkToMatchResults().withRel("allMatchResults"));
    }

    @GetMapping("/id/{id}")
    public EntityModel<MatchResultModel> findById(@PathVariable Long id) {
        return EntityModel.of(matchResultService.findById(id),
            matchResultLinkGenerator.linkToMatchResults().withRel("allMatchResults"));
    }

    @GetMapping("/filter")
    public CollectionModel<EntityModel<MatchResultModel>> findByFilter(
        @RequestParam(value = "teamId", required = false) Long teamId,
        @RequestParam(value = "matchId", required = false) Long matchId) {

        List<EntityModel<MatchResultModel>> matchResults =
            matchResultService.findByFilter(teamId, matchId).stream()
                .map(matchResul -> EntityModel.of(matchResul,
                    matchResultLinkGenerator.linkToMatchResultById(matchResul.getId())
                        .withRel("matchResultById")))
                .collect(
                    Collectors.toList());

        return CollectionModel.of(matchResults,
            matchResultLinkGenerator.linkToMatchResults().withRel("allMatchResults"));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,
                                        @RequestBody
                                        MatchResultEntity matchResultEntity) {
        matchResultService.updateById(id, matchResultEntity);
        return ResponseEntity.ok(matchResultLinkGenerator.linkToMatchResultById(id)
            .withRel("matchResultById"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        matchResultService.deleteByMatchId(id);
        return ResponseEntity.ok().build();
    }
}
