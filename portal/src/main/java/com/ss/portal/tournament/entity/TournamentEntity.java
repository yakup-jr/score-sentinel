package com.ss.portal.tournament.entity;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.team.entity.TeamEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(schema = "tournaments_schema", name = "tournaments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "tournaments_schema", name = "tournaments_teams",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<TeamEntity> teams;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "tournaments_schema", name = "tournaments_rounds",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "round_id"))
    private List<RoundEntity> rounds;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "tournaments_schema", name = "tournaments_games",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<GameEntity> games;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "tournaments_schema", name = "tournaments_tournament_results",
        joinColumns = @JoinColumn(name = "tournament_id"),
        inverseJoinColumns = @JoinColumn(name = "tournament_result_id"))
    private List<TournamentResultEntity> tournamentResults;

}
