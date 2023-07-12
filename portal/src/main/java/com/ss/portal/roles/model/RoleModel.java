package com.ss.portal.roles.model;

import com.ss.portal.roles.entity.RoleEntity;
import com.ss.portal.roles.enums.RoleEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleModel {
    private Long id;
    private RoleEnum name;

    public static RoleModel toModel(RoleEntity roleEntity) {
        RoleModel roleModel = new RoleModel();
        roleModel.setId(roleEntity.getId());
        roleModel.setName(roleEntity.getName());
        return roleModel;
    }

    public static List<RoleModel> toModel(List<RoleEntity> roleEntities) {
        List<RoleModel> roleModels = new ArrayList<>();

        for (int i = 0; i < roleEntities.size(); i++) {
            roleModels.add(RoleModel.toModel(roleEntities.get(i)));
        }

        return roleModels;
    }
}
