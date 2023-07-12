package com.ss.portal.user.model;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.roles.entity.RoleEntity;
import com.ss.portal.user.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String username;
    private String email;
    private List<RoleEntity> roles;
    private LocalDateTime createdAt;
    private LocalDateTime lastOnline;
    private List<GameEntity> games;
    private boolean isActive;

    public static UserModel toModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setEmail(userEntity.getEmail());
        userModel.setRoles(userEntity.getRoles());
        userModel.setCreatedAt(userEntity.getCreatedAt());
        userModel.setLastOnline(userEntity.getLastOnline());
        userModel.setGames(userEntity.getGames());
        userModel.setActive(userEntity.isActive());
        return userModel;
    }

    public static List<UserModel> toModel(List<UserEntity> userEntities) {
        ArrayList<UserModel> userModels = new ArrayList<>();

        for (int i = 0; i < userEntities.size(); i++) {
            userModels.add(UserModel.toModel(userEntities.get(i)));
        }

        return userModels;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getActive() {
        return this.isActive;
    }
}