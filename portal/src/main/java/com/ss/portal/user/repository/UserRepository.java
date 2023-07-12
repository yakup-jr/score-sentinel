package com.ss.portal.user.repository;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.roles.entity.RoleEntity;
import com.ss.portal.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // find
    Optional<UserEntity> findByEmail(@Param("email") String email);

    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query(
        "select u from UserEntity u where (:roles is null or u.roles in :roles) and " +
            "(:games is null or u.games in :games) and (:isActive is null or u" +
            ".isActive = :isActive) and (:isActive is not null or :roles is not null or" +
            " :games is not null)")
    Optional<List<UserEntity>> findByOptionalParameters(
        @Param("roles") List<RoleEntity> roles,
        @Param("games") List<GameEntity> games,
        @Param("isActive") Boolean isActive);

    @Modifying
    @Transactional
    @Query("update UserEntity u set u = :userEntity where u.id = :id")
    void updateById(@Param("id") Long id, @Param("user") UserEntity userEntity);
}
