package com.ss.portal.game.repository;


import com.ss.portal.game.entity.GameEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest(showSql = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
    }

    GameEntity gameEntity_1 = new GameEntity(1l, "fifa");
    GameEntity gameEntity_2 = new GameEntity(2l, "mc 11");

    void saveToRepository() {
        gameRepository.save(gameEntity_1);
        gameRepository.save(gameEntity_2);
    }

    @DisplayName("should return game when games exist")
    @CsvSource({"true,2", "false,0"})
    @ParameterizedTest
    void shouldReturnAllGamesWhenExist(boolean exist, int size) {
        if (exist) saveToRepository();

        List<GameEntity> excepted = gameRepository.findAll();

        assertThat(excepted.size()).isEqualTo(size);
    }

    @DisplayName("should return game when name exist")
    @CsvSource({"fifa,true", "some name,false", "null,false"})
    @ParameterizedTest
    void shouldReturnGameWhenNameExist(String name, boolean expected) {
        saveToRepository();

        Optional<GameEntity> actual =
            gameRepository.findByParams(name, null);

        assertThat(actual.isPresent()).isEqualTo(expected);
    }

    @DisplayName("should return game when id exist")
    @CsvSource({"1,true", "3,false"})
    @ParameterizedTest
    void shouldReturnGameWhenIdExist(Long id, boolean expected) {
        saveToRepository();

        Optional<GameEntity> actual =
            gameRepository.findByParams(null, id);

        assertThat(actual.isPresent()).isEqualTo(expected);
    }


    @DisplayName("should return game when id exist")
    @CsvSource(value = {"fifa 23,1,true", "new name,3,false"},
        nullValues = {
        "null"})
    @ParameterizedTest
    void shouldUpdateNameIfIdExist(String newName, Long id, boolean excepted) {
        saveToRepository();

        gameRepository.updateAllById(newName, id);
        Optional<GameEntity> actual = gameRepository.findByParams(newName, null);

        assertThat(actual.isPresent()).isEqualTo(excepted);
        if (actual.isPresent())
            assertThat(actual.get().getName()).isEqualTo(newName);
    }

    @DisplayName("should delete game when id or name exist")
    @CsvSource(value = {"fifa,null,false", "null,1,false", "fifa,1,false", "null,null," +
        "false", "fifa,2,false"},
        nullValues = {"null"})
    @ParameterizedTest
    void should(String name, Long id, boolean excepted) {
        saveToRepository();

        gameRepository.deleteByParams(name, id);
        Optional<GameEntity> actual = gameRepository.findByParams(name,
            id);

        assertThat(actual.isPresent()).isEqualTo(excepted);
    }
}
