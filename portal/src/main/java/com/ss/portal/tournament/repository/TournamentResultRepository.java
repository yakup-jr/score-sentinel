package com.ss.portal.tournament.repository;

import com.ss.portal.tournament.entity.TournamentResultEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentResultRepository
    extends JpaRepository<TournamentResultEntity, Long>,
    JpaSpecificationExecutor<TournamentResultEntity> {

    interface Specs {
        public static Specification<TournamentResultEntity> byTournamentId(
            Long tournamentId) {
            return (root, query, builder) -> {
                var joinTournament = root.join("tournaments");
                return builder.equal(joinTournament.get("id"), tournamentId);
            };
        }
    }

    @Modifying
    @Query("delete TournamentResultEntity tournamentResult where tournamentResult.id in" +
        " (select team.id from TeamEntity team where team.id = :teamId)")
    void deleteByTeamId(@Param("teamId") Long teamId);

    @Modifying
    @Query("delete TournamentResultEntity tournamentResult where tournamentResult.id in" +
        " (select tournament from TournamentEntity tournament where tournament.id = :tournamentId)")
    void deleteByTournamentId(@Param("tournamentId") Long tournamentId);
}
