package com.ss.portal.team.model;

import com.ss.portal.game.model.GameModel;
import com.ss.portal.team.entity.TeamEntity;
import com.ss.portal.user.model.UserModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamModel {
    private String name;
    private Long id;
    private List<UserModel> users;
    private List<GameModel> games;

    public static TeamModel toModel(TeamEntity team) {
        TeamModel teamToModel = new TeamModel();
        teamToModel.setName(team.getName());
        teamToModel.setId(team.getId());
        teamToModel.setUsers(UserModel.toModel(team.getUsers()));
        teamToModel.setGames(GameModel.toModel(team.getGames()));

        return teamToModel;
    }

    public static List<TeamModel> toModel(List<TeamEntity> teams) {
        List<TeamModel> teamsToModel = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            teamsToModel.add(TeamModel.toModel(teams.get(i)));
        }

        return teamsToModel;
    }
}
