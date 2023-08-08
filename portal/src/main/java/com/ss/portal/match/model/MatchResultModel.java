package com.ss.portal.match.model;

import com.ss.portal.match.entity.MatchEntity;
import com.ss.portal.match.entity.MatchResultEntity;
import com.ss.portal.team.entity.TeamEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchResultModel {
    private Long id;
    private int score;
    private float point;
    private TeamEntity team;
    private MatchEntity match;

    public static MatchResultModel toModel(MatchResultEntity matchResultEntity) {
        var matchResultModel = new MatchResultModel();

        matchResultModel.setId(matchResultEntity.getId());
        matchResultModel.setScore(matchResultEntity.getScore());
        matchResultModel.setPoint(matchResultEntity.getPoint());
        matchResultModel.setTeam(matchResultEntity.getTeam());
        matchResultModel.setMatch(matchResultEntity.getMatch());

        return matchResultModel;
    }

    public static List<MatchResultModel> toModel(
        List<MatchResultEntity> matchResultEntities) {
        var matchResultModels = new ArrayList<MatchResultModel>();

        for (MatchResultEntity matchResultEntity : matchResultEntities) {
            matchResultModels.add(toModel(matchResultEntity));
        }

        return matchResultModels;
    }
}
