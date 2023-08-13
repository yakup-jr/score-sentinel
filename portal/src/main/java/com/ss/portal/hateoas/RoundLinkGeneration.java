package com.ss.portal.hateoas;

import com.ss.portal.round.controller.RoundController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoundLinkGeneration {
    public static WebMvcLinkBuilder linkToRounds() {
        return linkTo(methodOn(RoundController.class).findByFilter(null, null, null,
            null));
    }

    public static WebMvcLinkBuilder linkToRoundById(Long id) {
        return linkTo(methodOn(RoundController.class).findById(id));
    }

    public static WebMvcLinkBuilder linkToRoundByFilter(Long matchId, Long teamId,
                                                        Long gameId, Long roundResultId) {
        return linkTo(methodOn(RoundController.class).findByFilter(matchId, teamId,
            gameId, roundResultId));
    }
}
