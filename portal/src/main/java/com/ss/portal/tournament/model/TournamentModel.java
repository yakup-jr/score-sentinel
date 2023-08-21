package com.ss.portal.tournament.model;

import com.ss.portal.game.model.GameModel;
import com.ss.portal.round.model.RoundModel;
import com.ss.portal.team.model.TeamModel;
import com.ss.portal.tournament.entity.TournamentEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TournamentModel {

    private Long id;
    private String name;
    private List<TeamModel> teams;
    private List<RoundModel> rounds;
    private List<GameModel> games;
    private List<TournamentResultModel> tournamentResults;

    public static TournamentModel toModel(TournamentEntity tournamentEntity) {
        var tournamentModel = new TournamentModel();

        tournamentModel.setId(tournamentEntity.getId());
        tournamentModel.setName(tournamentEntity.getName());
        tournamentModel.setTeams(tournamentEntity.getTeams() == null ? null :
            TeamModel.toModel(tournamentEntity.getTeams()));
        tournamentModel.setRounds(tournamentEntity.getRounds() == null ? null :
            RoundModel.toModel(tournamentEntity.getRounds()));
        tournamentModel.setGames(tournamentEntity.getGames() == null ? null :
            GameModel.toModel(tournamentEntity.getGames()));
        tournamentModel.setTournamentResults(
            tournamentEntity.getTournamentResults() == null ? null :
                TournamentResultModel.toModel(tournamentEntity.getTournamentResults()));

        return tournamentModel;
    }

    public static List<TournamentModel> toModel(
        List<TournamentEntity> tournamentEntities) {
        var tournamentModels = new ArrayList<TournamentModel>();

        for (TournamentEntity tournamentEntity : tournamentEntities) {
            tournamentModels.add(toModel(tournamentEntity));
        }

        return tournamentModels;
    }
}
