package com.ss.portal.hateoas;

import com.ss.portal.team.controller.TeamController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TeamLinkGenerator {

    public WebMvcLinkBuilder linkToTeams() {
        return linkTo(methodOn(TeamController.class).findAll());
    }

}
