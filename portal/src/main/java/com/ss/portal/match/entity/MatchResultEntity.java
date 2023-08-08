package com.ss.portal.match.entity;

import com.ss.portal.team.entity.TeamEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "matches_schema", name = "match_results")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;
    private float point;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(schema = "matches_schema", name = "match_results_teams", joinColumns =
    @JoinColumn(name = "match_result_id"), inverseJoinColumns = @JoinColumn(name =
        "team_id"))
    private TeamEntity team;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(schema = "matches_schema", name = "match_results_matches", joinColumns =
    @JoinColumn(name = "match_result_id"), inverseJoinColumns = @JoinColumn(name =
        "match_id"))
    private MatchEntity match;
}
