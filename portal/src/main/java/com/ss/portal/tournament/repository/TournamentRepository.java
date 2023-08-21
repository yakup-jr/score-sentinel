package com.ss.portal.tournament.repository;

import com.ss.portal.tournament.entity.TournamentEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, Long>,
    JpaSpecificationExecutor<TournamentEntity> {

    interface Specs {

        static Specification<TournamentEntity> byRoundId(Long roundId) {
            return (root, query, builder) -> {
                var joinRound = root.join("rounds");
                return builder.equal(joinRound.get("id"), roundId);
            };
        }

        static Specification<TournamentEntity> byTournamentResult(
            Long tournamentResultId) {
            return (root, query, builder) -> {
                var joinTournamentResult = root.join("tournamentResults");
                return builder.equal(joinTournamentResult.get("id"), tournamentResultId);
            };
        }
    }

    @Modifying
    @Query("delete TournamentEntity tournament where tournament.id in (select team from" +
        " TeamEntity team where team.id = :teamId)")
    void deleteByTeamId(@Param("teamId") Long teamId);

    @Modifying
    @Query("delete TournamentEntity tournament where tournament.id in (select game from" +
        " GameEntity game where game.id = :gameId)")
    void deleteByGameId(@Param("gameId") Long gameId);
}
