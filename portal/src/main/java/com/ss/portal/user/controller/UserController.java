package com.ss.portal.user.controller;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.hateoas.UserLinkGenerator;
import com.ss.portal.roles.entity.RoleEntity;
import com.ss.portal.user.entity.UserEntity;
import com.ss.portal.user.model.UserModel;
import com.ss.portal.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user-management/users")
public class UserController {

    private final UserService userService;
    private final UserLinkGenerator userLinkGenerator;

    @Autowired
    UserController(UserService userService, UserLinkGenerator userLinkGenerator) {
        this.userService = userService;
        this.userLinkGenerator = userLinkGenerator;
    }

    @PostMapping("/one")
    public EntityModel<UserModel> add(@RequestBody UserEntity userEntity) {
        return EntityModel.of(userService.add(userEntity),
            userLinkGenerator.linkToUsers().withRel("users"));
    }

    @GetMapping("/id/{id}")
    public EntityModel<UserModel> findById(@PathVariable Long id) {
        UserModel userModel = userService.findById(id);
        WebMvcLinkBuilder selfLink = linkTo(methodOn(UserController.class).findById(id));
        return EntityModel.of(userModel, selfLink.withSelfRel(),
            userLinkGenerator.linkToUsers().withRel("users"));
    }

    @GetMapping("/username/{username}")
    public EntityModel<UserModel> findByUsername(@PathVariable String username) {
        UserModel userModel = userService.findByUsername(username);
        WebMvcLinkBuilder selfLink =
            linkTo(methodOn(UserController.class).findByUsername(username));
        return EntityModel.of(userModel, selfLink.withSelfRel(),
            userLinkGenerator.linkToUsers().withRel("users"));
    }

    @GetMapping("/email/{email}")
    public EntityModel<UserModel> findByEmail(@PathVariable String email) {
        UserModel userModel = userService.findByEmail(email);
        WebMvcLinkBuilder selfLink =
            linkTo(methodOn(UserController.class).findByEmail(email));
        return EntityModel.of(userModel, selfLink.withSelfRel(),
            userLinkGenerator.linkToUsers().withRel("users"));
    }

    @GetMapping("/filter")
    public CollectionModel<EntityModel<UserModel>> findByFilters(
        @RequestParam(value = "roles", required = false) List<RoleEntity> roleEntities,
        @RequestParam(value = "games", required = false) List<GameEntity> gameEntities,
        @RequestParam(value = "isActive", required = false) boolean isActive) {
        List<EntityModel<UserModel>> users =
            userService.findByFilters(roleEntities, gameEntities, isActive).stream().map(
                    userModel -> EntityModel.of(userModel, linkTo(
                            methodOn(UserController.class).findById(
                                userModel.getId())).withSelfRel(),
                        userLinkGenerator.linkToUsers().withRel(
                            "users")))
                .collect(Collectors.toList());
        return CollectionModel.of(users, userLinkGenerator.linkToUsers().withSelfRel());
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<UserModel>> findAll() {
        List<EntityModel<UserModel>> users =
            userService.findAll().stream().map(user -> EntityModel.of(user,
                    linkTo(
                        methodOn(UserController.class).findById(user.getId())).withSelfRel(),
                    userLinkGenerator.linkToUsers().withRel("users")))
                .collect(Collectors.toList());
        return CollectionModel.of(users, userLinkGenerator.linkToUsers().withSelfRel());
    }

    @PatchMapping("/id/{id}")
    public EntityModel<UserModel> updateById(@PathVariable Long id,
                                             @RequestBody UserEntity updates) {
        UserModel user = userService.updateById(id, updates);
        WebMvcLinkBuilder selfLink =
            linkTo(methodOn(UserController.class).updateById(id, updates));

        return EntityModel.of(user, selfLink.withSelfRel(),
            userLinkGenerator.linkToUsers().withRel(
                "users"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(200).build();
    }
}
