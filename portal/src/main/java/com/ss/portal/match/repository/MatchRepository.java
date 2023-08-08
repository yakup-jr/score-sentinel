package com.ss.portal.match.repository;

import com.ss.portal.match.entity.MatchEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long>,
    JpaSpecificationExecutor<MatchEntity> {

    interface Specs {
        static Specification<MatchEntity> byTeamId(Long teamId) {
            return (root, query, builder) -> {
                var joinTeam = root.join("teams");
                return builder.equal(joinTeam, teamId);
            };
        }

        static Specification<MatchEntity> byGameId(Long gameId) {
            return (root, query, builder) -> {
                var joinGame = root.join("games");
                return builder.equal(joinGame, gameId);
            };
        }

        static Specification<MatchEntity> byMatchResultId(Long matchResultId) {
            return (root, query, builder) -> {
                var joinMatchResult = root.join("matchResults");
                return builder.equal(joinMatchResult, matchResultId);
            };
        }
    }

    @Modifying
    @Query("delete from MatchEntity match where match.id in (select team from " +
        "TeamEntity team where team.id = :teamId)")
    void deleteByTeamId(@Param("teamId") Long teamId);

    @Modifying
    @Query(
        "delete from MatchEntity match where match.id in (select game from GameEntity " +
            "game where game.id = :gameId)")
    void deleteByGameId(@Param("gameId") Long gameId);

    @Modifying
    @Query("delete from MatchEntity match where match.id in (select matchResult from " +
        "MatchResultEntity matchResult where matchResult.id = :matchResultId)")
    void deleteByMatchResultId(@Param("matchResultId") Long matchResultId);

}
