package com.ss.portal.hateoas;

import com.ss.portal.round.controller.RoundResultController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoundResultLinkGenerator {

    public static WebMvcLinkBuilder linkToRoundResults() {
        return linkTo(methodOn(RoundResultController.class).findByFilter(null, null));
    }

    public static WebMvcLinkBuilder linkToRoundResultsByFilter(Long teamId,
                                                               Long roundId) {
        return linkTo(methodOn(RoundResultController.class).findByFilter(teamId,
            roundId));
    }

    public static WebMvcLinkBuilder linkToRoundResultById(Long id) {
        return linkTo(methodOn(RoundResultController.class).findById(id));
    }

}
