package com.ss.portal.hateoas;

import com.ss.portal.match.controller.MatchController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MatchLinkGenerator {

    public WebMvcLinkBuilder linkToMatches() {
        return linkTo(methodOn(MatchController.class).findByFilter(null, null, null));
    }

    public WebMvcLinkBuilder linkToMatchById(Long id) {
        return linkTo(methodOn(MatchController.class).findById(id));
    }

    public WebMvcLinkBuilder linkToMatchByFilter(Long teamId, Long gameId,
                                                 Long matchResultId) {
        return linkTo(
            methodOn(MatchController.class).findByFilter(teamId, gameId, matchResultId));
    }

}
