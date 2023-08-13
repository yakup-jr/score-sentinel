package com.ss.portal.round.entity;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.match.entity.MatchEntity;
import com.ss.portal.team.entity.TeamEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(schema = "rounds_schema", name = "rounds")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "rounds_schema", name = "rounds_matches", joinColumns =
    @JoinColumn(name = "round_id"), inverseJoinColumns = @JoinColumn(name = "match_id"))
    private List<MatchEntity> matches;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "rounds_schema", name = "rounds_teams",
        joinColumns = @JoinColumn(name = "round_id"), inverseJoinColumns =
    @JoinColumn(name = "team_id"))
    private List<TeamEntity> teams;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "rounds_schema", name = "rounds_games",
        joinColumns = @JoinColumn(name = "round_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<GameEntity> games;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "rounds_schema", name = "rounds_round_results",
        joinColumns = @JoinColumn(name = "round_id"), inverseJoinColumns =
    @JoinColumn(name = "round_result_id"))
    private List<RoundResultEntity> roundResults;

}
