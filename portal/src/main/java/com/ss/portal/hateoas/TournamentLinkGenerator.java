package com.ss.portal.hateoas;

import com.ss.portal.tournament.controller.TournamentController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TournamentLinkGenerator {

    public static WebMvcLinkBuilder linkToTournaments() {
        return linkTo(
            methodOn(TournamentController.class).findByFilter(null, null, null, null));
    }

    public static WebMvcLinkBuilder linkToTournamentById(Long id) {
        return linkTo(methodOn(TournamentController.class).findById(id));
    }
}
