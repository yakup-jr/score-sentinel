package com.ss.portal.round.repository;

import com.ss.portal.round.entity.RoundEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<RoundEntity, Long>,
    JpaSpecificationExecutor<RoundEntity> {

    interface Specs {
        static Specification<RoundEntity> byRoundResultId(Long roundResultId) {
            return (root, query, builder) -> {
                var joinRoundResult = root.join("roundResults");
                return builder.equal(joinRoundResult.get("id"), roundResultId);
            };
        }
    }

    @Modifying
    @Query("delete RoundEntity r where r.id in (select match.id from MatchEntity match " +
        "where match.id = :matchId)")
    void deleteByMatchId(@Param("matchId") Long matchId);

    @Modifying
    @Query("delete RoundEntity r where r.id in (select team.id from TeamEntity team " +
        "where team.id = :teamId)")
    void deleteByTeamId(@Param("teamId") Long teamId);

    @Modifying
    @Query("delete RoundEntity r where r.id in (select game.id from GameEntity game " +
        "where game.id = :gameId)")
    void deleteByGameId(@Param("gameId") Long gameId);
}
