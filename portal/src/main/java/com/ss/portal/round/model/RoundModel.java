package com.ss.portal.round.model;

import com.ss.portal.game.model.GameModel;
import com.ss.portal.match.model.MatchModel;
import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.team.model.TeamModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoundModel {

    private Long id;
    private String name;
    private List<MatchModel> matches;
    private List<TeamModel> teams;
    private List<GameModel> games;
    private List<RoundResultModel> roundResults;

    public static RoundModel toModel(RoundEntity roundEntity) {
        var roundModel = new RoundModel();

        roundModel.setId(roundEntity.getId());
        roundModel.setName(roundEntity.getName());
        roundModel.setMatches(roundEntity.getMatches() == null ? null :
            MatchModel.toModel(roundEntity.getMatches()));
        roundModel.setTeams(roundEntity.getTeams() == null ? null :
            TeamModel.toModel(roundEntity.getTeams()));
        roundModel.setGames(roundEntity.getGames() == null ? null :
            GameModel.toModel(roundEntity.getGames()));
        roundModel.setRoundResults(roundEntity.getRoundResults() == null ? null :
            RoundResultModel.toModel(roundEntity.getRoundResults()));

        return roundModel;
    }

    public static List<RoundModel> toModel(List<RoundEntity> roundEntities) {
        var roundModels = new ArrayList<RoundModel>();

        for (RoundEntity roundEntity : roundEntities) {
            roundModels.add(toModel(roundEntity));
        }

        return roundModels;
    }

}
