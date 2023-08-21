package com.ss.portal.tournament.entity;

import com.ss.portal.team.entity.TeamEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "tournaments_schema", name = "tournament_results")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    private float points;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "tournaments_schema", name = "tournament_results_teams",
        joinColumns = @JoinColumn(name = "tournament_result_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id"))
    private TeamEntity team;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "tournaments_schema", name = "tournaments_tournament_results",
        joinColumns = @JoinColumn(name = "tournament_result_id"),
        inverseJoinColumns = @JoinColumn(name = "tournament_id"))
    private TournamentEntity tournament;

}
