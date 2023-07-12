package com.ss.portal.game.model;

import com.ss.portal.game.entity.GameEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GameModel {
    private Long id;
    private String name;

    public static GameModel toModel(GameEntity gameEntity) {
        GameModel gameModel = new GameModel();
        gameModel.setId(gameEntity.getId());
        gameModel.setName(gameEntity.getName());
        return gameModel;
    }

    public static List<GameModel> toModel(List<GameEntity> gameEntities) {
        List<GameModel> gameModels = new ArrayList<>();

        for (GameEntity gameEntity : gameEntities) {
            gameModels.add(GameModel.toModel(gameEntity));
        }

        return gameModels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
