package com.ss.portal.team.repository;


import com.ss.portal.team.entity.TeamEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.ss.portal.team.repository.TeamRepository.Specs.byGameId;
import static com.ss.portal.team.repository.TeamRepository.Specs.byUserId;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = {"/data/add-users.sql", "/data/add-games.sql", "/data/add-teams.sql",
    "/data/ref-teams-games.sql", "/data/ref-teams-users.sql"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    void tearDown() {
        testEntityManager.clear();
    }

    @ParameterizedTest
    @MethodSource("provideName")
    void should_return_teams_when_name_exists(String name, boolean exceptedFound) {
        // when
        Optional<TeamEntity> team = teamRepository.findByName(name);

        // then
        assertThat(team.isPresent()).isEqualTo(exceptedFound);
        if (exceptedFound)
            assertThat(team.get().getName()).isEqualTo(name);
    }

    public static Stream<Arguments> provideName() {
        return Stream.of(
            Arguments.of("team1", true),
            Arguments.of("none", false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUserId")
    void should_return_teams_when_userId_exists(Long userId, int exceptedSize) {
        // when
        List<TeamEntity> teams = teamRepository.findAll(byUserId(userId));

        // then
        assertThat(teams.size()).isEqualTo(exceptedSize);
        if (exceptedSize != 0) {
            assertThat(teams.get(0).getUsers().stream()
                .anyMatch(user -> Objects.equals(user.getId(), userId))).isTrue();
        }
    }

    public static Stream<Arguments> provideUserId() {
        return Stream.of(
            Arguments.of(1L, 1),
            Arguments.of(2L, 1),
            Arguments.of(3L, 2),
            Arguments.of(4L, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideGameIds")
    void should_return_teams_when_gameIds_exists(Long gameId, int exceptedSize) {
        // when
        List<TeamEntity> teams = teamRepository.findAll(byGameId(gameId));

        // then
        assertThat(teams.size()).isEqualTo(exceptedSize);
        if (exceptedSize != 0) {
            assertThat(teams.get(0).getGames().stream()
                .anyMatch(game -> Objects.equals(game.getId(), gameId))).isTrue();
        }
    }

    public static Stream<Arguments> provideGameIds() {
        return Stream.of(
            Arguments.of(1L, 1),
            Arguments.of(3L, 2),
            Arguments.of(2L, 2),
            Arguments.of(4L, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUserIdAndGameIds")
    void should_return_team_when_userId_and_gameIds_exists(Long userId,
                                                           Long gameId,
                                                           int exceptedSize) {
        // when
        List<TeamEntity> teams =
            teamRepository.findAll(byUserId(userId).and(byGameId(gameId)));

        // then
        assertThat(teams.size()).isEqualTo(exceptedSize);
        if (exceptedSize != 0) {
            assertThat(teams.get(0).getUsers().stream()
                .anyMatch(user -> Objects.equals(user.getId(), userId))).isTrue();
            assertThat(teams.get(0).getGames().stream()
                .anyMatch(game -> Objects.equals(game.getId(), gameId))).isTrue();
        }
    }

    public static Stream<Arguments> provideUserIdAndGameIds() {
        return Stream.of(
            Arguments.of(1L, 1L, 1),
            Arguments.of(2L, 2L, 1),
            Arguments.of(3L, 2L, 2),
            Arguments.of(4L, 1L, 0),
            Arguments.of(1L, 4L, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideNameAndIdUpdates")
    void should_update_name_when_name_and_id_exists(String newName, Long id) {
        // when
        teamRepository.updateNameById(newName, id);

        Optional<TeamEntity> team = teamRepository.findById(id);

        // then
        assertThat(team.isPresent()).isTrue();
        assertThat(team.get().getName()).isEqualTo(newName);
    }

    public static Stream<Arguments> provideNameAndIdUpdates() {
        return Stream.of(
            Arguments.of("new name team", 1L),
            Arguments.of("", 2L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDeletionName")
    void should_delete_team_when_name_exists(String name) {
        // when
        teamRepository.deleteByName(name);

        Optional<TeamEntity> team = teamRepository.findByName(name);

        // then
        assertThat(team.isPresent()).isFalse();
    }

    public static Stream<Arguments> provideDeletionName() {
        return Stream.of(
            Arguments.of("team1"),
            Arguments.of("none")
        );
    }
}
