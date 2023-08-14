package com.ss.portal.tournament.model;

import com.ss.portal.team.model.TeamModel;
import com.ss.portal.tournament.entity.TournamentResultEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TournamentResultModel {

    private Long id;
    private int score;
    private float points;
    private TeamModel team;
    private TournamentModel tournament;

    public static TournamentResultModel toModel(
        TournamentResultEntity tournamentResultEntity) {
        var tournamentResultModel = new TournamentResultModel();

        tournamentResultModel.setId(tournamentResultEntity.getId());
        tournamentResultModel.setScore(tournamentResultEntity.getScore());
        tournamentResultModel.setPoints(tournamentResultEntity.getPoints());
        tournamentResultModel.setTeam(tournamentResultEntity.getTeam() == null ? null :
            TeamModel.toModel(tournamentResultEntity.getTeam()));
        tournamentResultModel.setTournament(
            tournamentResultEntity.getTournament() == null ? null :
                TournamentModel.toModel(tournamentResultEntity.getTournament()));

        return tournamentResultModel;
    }

    public static List<TournamentResultModel> toModel(
        List<TournamentResultEntity> tournamentResultEntities) {
        var tournamentResultModels = new ArrayList<TournamentResultModel>();

        for (TournamentResultEntity tournamentResultEntity : tournamentResultEntities) {
            tournamentResultModels.add(toModel(tournamentResultEntity));
        }

        return tournamentResultModels;
    }

}
