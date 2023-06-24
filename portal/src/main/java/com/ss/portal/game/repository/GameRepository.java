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

    Optional<GameEntity> findByName(@Param("name") String name);

    @Query("select GameEntity from GameEntity games where (:name is null " +
        "or games.name = :name) and (:id is null or games.id = :id)")
    Optional<GameEntity> findByParams(@Param("name") String name,
                                        @Param(
                                            "id") Long id);

    @Transactional
    @Modifying
    @Query(
        "update GameEntity games set games.name = :newName where games.name " +
            "= " +
            ":name or games.id = :id")
    void updateAllByNameOrId(
        @Param("newName") String newName,
        @Param("name") String name,
        @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(
        "update GameEntity games set games.name = :newName where games.id = :id")
    void updateNameById(@Param("newName") String name, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(
        "update GameEntity games set games.name = :newName where games.name = :oldName")
    void updateNameByName(@Param("newName") String newName,
                          @Param("oldName") String oldName);

    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete GameEntity games where games.name = :name")
    void deleteByName(@Param("name") String name);
}
