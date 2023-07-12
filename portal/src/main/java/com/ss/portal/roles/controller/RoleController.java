package com.ss.portal.roles.controller;

import com.ss.portal.hateoas.RoleLinkGenerator;
import com.ss.portal.roles.enums.RoleEnum;
import com.ss.portal.roles.model.RoleModel;
import com.ss.portal.roles.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/role-management/roles")
public class RoleController {

    private final RoleService roleService;

    private final RoleLinkGenerator roleLinkGenerator;

    @Autowired
    RoleController(RoleService roleService, RoleLinkGenerator roleLinkGenerator) {
        this.roleService = roleService;
        this.roleLinkGenerator = roleLinkGenerator;
    }

    @GetMapping("/id/{id}")
    public EntityModel<RoleModel> findById(@PathVariable Long id) {
        RoleModel role = roleService.findById(id);
        WebMvcLinkBuilder selfLink = linkTo(
            WebMvcLinkBuilder.methodOn(RoleController.class).findById(id));
        return EntityModel.of(role, selfLink.withSelfRel(),
            roleLinkGenerator.linkToRoles().withRel("roles"));
    }

    @GetMapping("/name/{name}")
    public EntityModel<RoleModel> findByName(@PathVariable RoleEnum name) {
        RoleModel role = roleService.findByName(name);
        WebMvcLinkBuilder selfLink = linkTo(
            WebMvcLinkBuilder.methodOn(RoleController.class).findByName(name));
        return EntityModel.of(role, selfLink.withSelfRel(),
            roleLinkGenerator.linkToRoles().withRel("roles"));
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<RoleModel>> findAll() {
        List<EntityModel<RoleModel>> roles = roleService.findAll().stream()
            .map(role -> EntityModel.of(role,
                roleLinkGenerator.linkToRoles().withSelfRel()))
            .collect(Collectors.toList());
        return CollectionModel.of(roles,
            roleLinkGenerator.linkToRoles().withRel("roles"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable RoleEnum name) {
        roleService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
}
