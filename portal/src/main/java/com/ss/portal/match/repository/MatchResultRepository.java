package com.ss.portal.match.repository;

import com.ss.portal.match.entity.MatchResultEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResultEntity, Long>,
    JpaSpecificationExecutor<MatchResultEntity> {

    interface Specs {

        static Specification<MatchResultEntity> byTeamId(Long teamId) {
            return (root, query, builder) -> {
                var teamJoin = root.join("users");
                return builder.equal(teamJoin.get("teamId"), teamId);
            };
        }

        static Specification<MatchResultEntity> byMatchId(Long matchId) {
            return (root, query, builder) -> {
                var matchJoin = root.join("match");
                return builder.equal(matchJoin.get("id"), matchId);
            };
        }


    }

    // todo:
    @Modifying
    @Query("delete from MatchResultEntity matchResult where matchResult = :teamId")
    void deleteByTeamId(@Param("teamId") Long teamId);

    @Modifying
    @Query("delete from MatchResultEntity matchResult where matchResult = :matchId")
    void deleteByMatchId(@Param("matchId") Long matchId);
}
