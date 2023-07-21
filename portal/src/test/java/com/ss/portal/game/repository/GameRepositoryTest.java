package com.ss.portal.game.repository;

import com.ss.portal.game.entity.GameEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataJpaTest
@Sql("/data/add-games.sql")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
        testEntityManager.clear();
    }

    @Test
    void should_return_all_games_when_exist() {
        List<GameEntity> excepted = gameRepository.findAll();

        assertThat(excepted.size()).isEqualTo(2);
    }

    @ParameterizedTest
    @MethodSource("gameParams")
    void should_return_game_by_params_when_exists(String name, Long id,
                                                  Long expectedId) {
        // when
        Optional<GameEntity> found = gameRepository.findByParams(name, id);
        if (expectedId == null)
            assertThat(found).isEmpty();
        else
            assertThat(found.map(GameEntity::getId)).hasValue(expectedId);

    }

    static Stream<Arguments> gameParams() {
        return Stream.of(
            Arguments.of("mc 11", null, 2L),
            Arguments.of("fifa", null, 1L),
            Arguments.of(null, 2L, 2L),
            Arguments.of(null, null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("gameUpdateParams")
    void should_update_game_name_by_id_when_exists(Long id, String newName) {
        // when
        assertThatCode(() -> gameRepository.updateAllById(newName,
            id)).doesNotThrowAnyException();
        testEntityManager.clear();

        // then
        Optional<GameEntity> updated = gameRepository.findById(id);
        if (newName == null)
            assertThat(updated).isEmpty();
        else
            assertThat(updated.map(GameEntity::getName)).hasValue(newName);
    }

    static Stream<Arguments> gameUpdateParams() {
        return Stream.of(
            Arguments.of(1L, "Mario"),
            Arguments.of(2L, "mc 11"),
            Arguments.of(3L, null)
        );
    }

    @ParameterizedTest
    @MethodSource("gameDeleteParams")
    void should_delete_game_by_params_when_exists(String name, Long id,
                                                  int expectedSize) {
        // when
        assertThatCode(() -> gameRepository.deleteByParams(name,
            id)).doesNotThrowAnyException();

        // then
        List<GameEntity> games = gameRepository.findAll();
        assertThat(games.size()).isEqualTo(expectedSize);
    }

    static Stream<Arguments> gameDeleteParams() {
        return Stream.of(
            Arguments.of("fifa", null, 1),
            Arguments.of(null, 2L, 1),
            Arguments.of("mc 11", 1L, 2),
            Arguments.of(null, null, 2)
        );
    }
}
