package com.ss.portal.hateoas;

import com.ss.portal.roles.controller.RoleController;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleLinkGenerator {

    public WebMvcLinkBuilder linkToRoles() {
        return linkTo(methodOn(RoleController.class).findAll());
    }
}
