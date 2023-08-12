package com.ss.portal.round.model;

import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.round.entity.RoundResultEntity;
import com.ss.portal.team.entity.TeamEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RoundResultModel {

    private Long id;
    private int score;
    private float points;
    private TeamEntity team;
    private RoundEntity round;

    public static RoundResultModel toModel(RoundResultEntity roundResultEntity) {
        var roundResultModel = new RoundResultModel();
        roundResultModel.setId(roundResultEntity.getId());
        roundResultModel.setScore(roundResultEntity.getScore());
        roundResultModel.setPoints(roundResultEntity.getPoints());
        roundResultModel.setTeam(roundResultEntity.getTeam());
        roundResultModel.setRound(roundResultEntity.getRound());

        return roundResultModel;
    }

    public static List<RoundResultModel> toModel(
        List<RoundResultEntity> roundResultEntities) {
        var roundResultModels = new ArrayList<RoundResultModel>();

        for (RoundResultEntity roundResultEntity : roundResultEntities) {
            roundResultModels.add(toModel(roundResultEntity));
        }

        return roundResultModels;
    }

}
