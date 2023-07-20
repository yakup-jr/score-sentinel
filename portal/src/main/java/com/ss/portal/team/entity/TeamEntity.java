package com.ss.portal.team.entity;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(schema = "teams_schema", name = "teams")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(schema = "teams_users_schema", name = "teams_users",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> users;

    @ManyToMany
    @JoinTable(schema = "teams_games_schema", name = "teams_games",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<GameEntity> games;

    @PostPersist
    public void postPersist() {
        this.name = this.name + "-" + this.id;
    }
}
