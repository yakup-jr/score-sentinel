package com.ss.portal.hateoas;

import com.ss.portal.match.controller.MatchResultController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MatchResultLinkGenerator {

    public WebMvcLinkBuilder linkToMatchResults() {
        return linkTo(methodOn(MatchResultController.class).findByFilter(null, null));
    }

    public WebMvcLinkBuilder linkToMatchResultById(Long id) {
        return linkTo(methodOn(MatchResultController.class).findById(id));
    }

    public WebMvcLinkBuilder linkToMatchResultsByFilter(Long teamId, Long matchId) {
        return linkTo(methodOn(MatchResultController.class).findByFilter(teamId,
            matchId));
    }

}
