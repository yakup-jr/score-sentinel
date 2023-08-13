package com.ss.portal.round.repository;

import com.ss.portal.round.entity.RoundResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundResultRepository extends JpaRepository<RoundResultEntity,
    Long>, JpaSpecificationExecutor<RoundResultEntity> {

    @Modifying
    @Query("delete from RoundResultEntity roundResult where roundResult.id in (select " +
        "team.id from TeamEntity team where team.id = :teamId)")
    void deleteByTeamId(@Param("teamId") Long teamId);

    @Modifying
    @Query("delete from RoundResultEntity roundResult where roundResult.id in (select " +
        "r.id from RoundEntity r where r.id = :roundId)")
    void deleteByRoundId(@Param("roundId") Long roundId);

}
