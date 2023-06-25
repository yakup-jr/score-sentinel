package com.ss.portal.game.repository;

import com.ss.portal.game.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    List<GameEntity> findAll();

    @Query("select g from GameEntity g where (:name is null or g.name = :name) and (:id is null or g.id = :id)")
    Optional<GameEntity> findByParams(@Param("name") String name,
                                      @Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update GameEntity g set g.name = :newName where (:id is null or g.id = :id)")
    void updateAllById(
            @Param("newName") String newName,
            @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete GameEntity games where (:name is null or games.name = :name) and " +
        "(:id is null or games.id = :id)")
    void deleteByParams(@Param("name") String name, @Param("id") Long id);

    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete GameEntity games where games.name = :name")
    void deleteByName(@Param("name") String name);
}
