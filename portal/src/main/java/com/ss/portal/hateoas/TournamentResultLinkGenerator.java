package com.ss.portal.hateoas;

import com.ss.portal.tournament.controller.TournamentResultController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TournamentResultLinkGenerator {

    public static WebMvcLinkBuilder linkToTournamentResults() {
        return linkTo(methodOn(TournamentResultController.class).findByFilter(null,
            null));
    }

    public static WebMvcLinkBuilder linkToTournamentResultById(Long id) {
        return linkTo(methodOn(TournamentResultController.class).findById(id));
    }

}
