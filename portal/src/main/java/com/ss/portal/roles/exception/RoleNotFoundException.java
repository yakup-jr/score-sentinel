package com.ss.portal.roles.exception;

import com.ss.portal.roles.enums.RoleEnum;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Long id) {
        super("Role with id " + id + " not found");
    }

    public RoleNotFoundException(RoleEnum name) {
        super("Role with name " + name + " not found");
    }
}
