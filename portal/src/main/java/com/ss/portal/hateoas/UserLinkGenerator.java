package com.ss.portal.hateoas;

import com.ss.portal.user.controller.UserController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkGenerator {

    public WebMvcLinkBuilder linkToUsers() {
        return linkTo(methodOn(UserController.class).findAll());
    }

}
