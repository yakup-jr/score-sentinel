package com.ss.portal.team.repository;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.team.entity.TeamEntity;
import com.ss.portal.user.entity.UserEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long>,
    JpaSpecificationExecutor<TeamEntity> {

    interface Specs {
        static Specification<TeamEntity> byUserId(Long userId) {
            return (root, query, cb) -> {
                Join<TeamEntity, UserEntity> userJoin = root.join("users");
                return cb.equal(userJoin.get("id"), userId);
            };
        }

        static Specification<TeamEntity> byGameId(Long gameId) {
            return (root, query, cb) -> {
                Join<TeamEntity, GameEntity> gameJoin = root.join("games");
                return cb.equal(gameJoin.get("id"), gameId);
            };
        }
    }

    @Query("select t from TeamEntity t where t.name = :name")
    Optional<TeamEntity> findByName(@Param("name") String name);

    @Modifying
    @Query("update TeamEntity t set t.name = :newName where t.id = :id")
    void updateNameById(@Param("newName") String newName, @Param("id") Long id);

    @Modifying
    @Query("delete from TeamEntity t where t.name = :name")
    void deleteByName(@Param("name") String name);

}
