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
    @JoinTable(name = "team")
    private TeamEntity team;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "match")
    private MatchEntity match;
}
