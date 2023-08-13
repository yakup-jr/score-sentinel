package com.ss.portal.round.entity;

import com.ss.portal.team.entity.TeamEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "rounds_schema", name = "round_results")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;
    private float points;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "rounds_schema", name = "round_results_teams",
        joinColumns = @JoinColumn(name = "round_result_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id"))
    private TeamEntity team;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(schema = "rounds_schema", name = "round_results_rounds", joinColumns =
    @JoinColumn(name = "round_result_id"), inverseJoinColumns = @JoinColumn(name =
        "round_id"))
    private RoundEntity round;
}
