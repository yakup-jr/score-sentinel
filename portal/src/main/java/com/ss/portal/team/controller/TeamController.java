package com.ss.portal.team.controller;

import com.ss.portal.hateoas.TeamLinkGenerator;
import com.ss.portal.team.entity.TeamEntity;
import com.ss.portal.team.model.TeamModel;
import com.ss.portal.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/team-management/teams")
public class TeamController {

    private final TeamService teamService;

    private final TeamLinkGenerator teamLinkGenerator;

    @Autowired
    public TeamController(TeamService teamService, TeamLinkGenerator teamLinkGenerator) {
        this.teamService = teamService;
        this.teamLinkGenerator = teamLinkGenerator;
    }


    // Todo: @Valid vs @Validated
    @PostMapping("/one")
    public EntityModel<TeamModel> save(@RequestBody TeamEntity teamCreate) {
        return EntityModel.of(teamService.save(teamCreate),
            teamLinkGenerator.linkToTeams().withRel("teams"));
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<TeamModel>> findAll() {

        List<EntityModel<TeamModel>> teams = teamService.findAll().stream()
            .map(team -> EntityModel.of(team,
                teamLinkGenerator.linkToTeams().withSelfRel()))
            .collect(Collectors.toList());


        return CollectionModel.of(teams,
            teamLinkGenerator.linkToTeams().withRel("teams"));
    }

    @GetMapping("/id/{id}")
    public EntityModel<TeamModel> findById(@PathVariable Long id) {
        TeamModel team = teamService.findById(id);

        WebMvcLinkBuilder selfLink =
            linkTo(methodOn(TeamController.class).findById(id));

        return EntityModel.of(team, selfLink.withSelfRel(),
            teamLinkGenerator.linkToTeams().withRel("teams"));
    }

    @GetMapping("/name/{name}")
    public EntityModel<TeamModel> findByName(@PathVariable String name) {
        TeamModel team = teamService.findByName(name);

        WebMvcLinkBuilder selfLink =
            linkTo(methodOn(TeamController.class).findByName(name));

        return EntityModel.of(team, selfLink.withSelfRel(),
            teamLinkGenerator.linkToTeams().withRel("teams"));
    }

    @GetMapping("/filter")
    public CollectionModel<EntityModel<TeamModel>> findByFilter(
        @RequestParam(value = "userId", required = false) Long userId,
        @RequestParam(value = "gameId", required = false) Long gameId) {
        WebMvcLinkBuilder selfLink =
            linkTo(methodOn(TeamController.class)
                .findByFilter(userId, gameId));

        List<EntityModel<TeamModel>> teams =
            teamService.findByFilter(userId, gameId).stream()
                .map(team -> EntityModel.of(team, selfLink.withSelfRel(),
                    teamLinkGenerator.linkToTeams().withRel("teams")))
                .collect(Collectors.toList());

        return CollectionModel.of(teams,
            teamLinkGenerator.linkToTeams().withRel("teams"));
    }

    @PutMapping("/id/{id}")
    public EntityModel<TeamModel> updateById(@PathVariable Long id,
                                             @RequestBody TeamEntity teamEntity) {
        WebMvcLinkBuilder selfLink =
            linkTo(methodOn(TeamController.class).updateById(id, teamEntity));

        return EntityModel.of(teamService.updateNameById(id, teamEntity),
            selfLink.withSelfRel(),
            teamLinkGenerator.linkToTeams().withRel("teams"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        teamService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name) {
        teamService.deleteByName(name);

        return ResponseEntity.noContent().build();
    }
}
