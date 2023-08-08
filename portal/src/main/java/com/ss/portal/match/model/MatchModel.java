package com.ss.portal.match.model;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.match.entity.MatchEntity;
import com.ss.portal.match.entity.MatchResultEntity;
import com.ss.portal.team.entity.TeamEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchModel {
    private Long id;
    private String name;
    private String score;
    private List<TeamEntity> teams;
    private List<GameEntity> games;
    private List<MatchResultEntity> matchResults;

    public static MatchModel toModel(MatchEntity matchEntity) {
        var matchModel = new MatchModel();

        matchModel.setName(matchEntity.getName());
        matchModel.setScore(matchEntity.getScore());
        matchModel.setTeams(matchEntity.getTeams());
        matchModel.setGames(matchEntity.getGames());
        matchModel.setMatchResults(matchEntity.getMatchResults());
        matchEntity.setId(matchEntity.getId());

        return matchModel;
    }

    public static List<MatchModel> toModel(List<MatchEntity> matchEntities) {
        var matchModels = new ArrayList<MatchModel>();

        for (MatchEntity matchEntity: matchEntities) {
            matchModels.add(toModel(matchEntity));
        }

        return matchModels;
    }

}
