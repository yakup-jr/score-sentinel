package com.ss.portal.tournament.service;

import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.tournament.entity.TournamentEntity;
import com.ss.portal.tournament.exception.TournamentNofFoundException;
import com.ss.portal.tournament.model.TournamentModel;
import com.ss.portal.tournament.repository.TournamentRepository;
import com.ss.portal.user.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.ss.portal.shared.repository.SharedRepository.Specs.byTeamId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private TournamentService tournamentService;


    @Test
    @DisplayName("Should create a specification with only teamId not null")
    void createSpecificationWithOnlyTeamIdNotNull() {
        Long teamId = 1L;
        Long roundId = null;
        Long gameId = null;
        Long tournamentResultId = null;

        Specification<TournamentEntity> expectedSpec =
            Specification.where(null).and(byTeamId(teamId));

        Specification<TournamentEntity> actualSpec =
            tournamentService.createSpecification(teamId, roundId, gameId,
                tournamentResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should create a specification with all parameters null")
    void createSpecificationWithAllParametersNull() {
        Long teamId = null;
        Long roundId = null;
        Long gameId = null;
        Long tournamentResultId = null;

        Specification<TournamentEntity> expectedSpec = Specification.where(null);

        Specification<TournamentEntity> actualSpec =
            tournamentService.createSpecification(teamId, roundId, gameId,
                tournamentResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should create a specification with only tournamentResultId not null")
    void createSpecificationWithOnlyTournamentResultIdNotNull() {
        Long teamId = null;
        Long roundId = null;
        Long gameId = null;
        Long tournamentResultId = 1L;

        Specification<TournamentEntity> expectedSpec = Specification.where(null)
            .and(tournamentService.byTournamentResult(tournamentResultId));

        Specification<TournamentEntity> actualSpec =
            tournamentService.createSpecification(
                teamId, roundId, gameId, tournamentResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should create a specification with only roundId not null")
    void createSpecificationWithOnlyRoundIdNotNull() {
        Long teamId = null;
        Long roundId = 1L;
        Long gameId = null;
        Long tournamentResultId = null;

        Specification<TournamentEntity> expectedSpec = Specification
            .where(null)
            .and(tournamentService.byRoundId(roundId));

        Specification<TournamentEntity> actualSpec =
            tournamentService.createSpecification(
                teamId, roundId, gameId, tournamentResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should create a specification with only gameId not null")
    void createSpecificationWithOnlyGameIdNotNull() {
        Long teamId = null;
        Long roundId = null;
        Long gameId = 1L;
        Long tournamentResultId = null;

        Specification<TournamentEntity> expectedSpec =
            Specification.where(null).and(tournamentService.byGameId(gameId));

        Specification<TournamentEntity> actualSpec =
            tournamentService.createSpecification(teamId, roundId, gameId,
                tournamentResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should throw an exception when the id does not exist")
    void updateByIdWhenIdDoesNotExistThenThrowException() {
        Long id = 1L;
        TournamentEntity tournament = new TournamentEntity();
        tournament.setId(id);
        tournament.setName("Test Tournament");
        tournament.setTeams(List.of());
        tournament.setRounds(List.of());
        tournament.setGames(List.of());
        tournament.setTournamentResults(List.of());

        when(tournamentRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(TournamentNofFoundException.class, () -> {
            tournamentService.updateById(id, tournament);
        });

        verify(tournamentRepository, never()).save(any(TournamentEntity.class));
    }

    @Test
    @DisplayName("Should update the tournament when the id exists")
    void updateByIdWhenIdExists() {
        Long id = 1L;
        TournamentEntity existingTournament = new TournamentEntity();
        existingTournament.setId(id);
        existingTournament.setName("Tournament 1");
        existingTournament.setTeams(
            List.of(new UserEntity("User 1"), new UserEntity("User 2")));
        existingTournament.setRounds(
            List.of(new RoundEntity("Round 1"), new RoundEntity("Round 2")));
        existingTournament.setGames(List.of(new
    }

    @Test
    @DisplayName("Should return tournaments filtered by roundId")
    void findByFilterWhenFilteredByRoundId() {
        Long roundId = 1L;
        List<TournamentEntity> expectedTournaments = List.of(
            new TournamentEntity(1L, "Tournament 1", null, null, null, null),
            new TournamentEntity(2L, "Tournament 2", null, null, null, null)
        );

        when(tournamentRepository.findAll(any(Specification.class)))
            .thenReturn(expectedTournaments);

        List<TournamentModel> result =
            tournamentService.findByFilter(null, roundId, null, null);

        assertEquals(expectedTournaments.size(), result.size());
        assertEquals(expectedTournaments.get(0).getId(), result.get(0).getId());
        assertEquals(expectedTournaments.get(0).getName(), result.get(0).getName());
        assertEquals(expectedTournaments.get(1).getId(), result.get(1).getId());
        assertEquals(expectedTournaments.get(1).getName(), result.get(1).getName());

        verify(tournamentRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return all tournaments when no filter is provided")
    void findByFilterWhenNoFilterIsProvided() {
        Long teamId = null;
        Long roundId = null;
        Long gameId = null;
        Long tournamentResultId = null;

        List<TournamentEntity> tournamentEntities = List.of(
            new TournamentEntity(1L, "Tournament 1", null, null, null, null),
            new TournamentEntity(2L, "Tournament 2", null, null, null, null),
            new TournamentEntity(3L, "Tournament 3", null, null, null, null)
        );

        when(tournamentRepository.findAll(any(Specification.class)))
            .thenReturn(tournamentEntities);

        List<TournamentModel> result = tournamentService.findByFilter(
            teamId, roundId, gameId, tournamentResultId);

        assertEquals(3, result.size());
        assertEquals("Tournament 1", result.get(0).getName());
        assertEquals("Tournament 2", result.get(1).getName());
        assertEquals("Tournament 3", result.get(2).getName());

        verify(tournamentRepository, times(1))
            .findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return tournaments filtered by gameId")
    void findByFilterWhenFilteredByGameId() {
        Long gameId = 1L;
        List<TournamentEntity> tournamentEntities = List.of(
            new TournamentEntity(1L, "Tournament 1", null, null, null, null),
            new TournamentEntity(2L, "Tournament 2", null, null, null, null)
        );

        when(tournamentRepository.findAll(any(Specification.class))).thenReturn(
            tournamentEntities);

        List<TournamentModel> result =
            tournamentService.findByFilter(null, null, gameId, null);

        assertEquals(2, result.size());
        assertEquals("Tournament 1", result.get(0).getName());
        assertEquals("Tournament 2", result.get(1).getName());

        verify(tournamentRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return tournaments filtered by teamId")
    void findByFilterWhenFilteredByTeamId() {
        Long teamId = 1L;

        List<TournamentEntity> tournamentEntities = List.of(
            new TournamentEntity(1L, "Tournament 1", null, null, null, null),
            new TournamentEntity(2L, "Tournament 2", null, null, null, null)
        );

        when(tournamentRepository.findAll(any(Specification.class)))
            .thenReturn(tournamentEntities);

        List<TournamentModel> expectedTournaments = List.of(
            new TournamentModel(1L, "Tournament 1", null, null, null, null),
            new TournamentModel(2L, "Tournament 2", null, null, null, null)
        );

        List<TournamentModel> actualTournaments =
            tournamentService.findByFilter(teamId, null, null, null);

        assertEquals(expectedTournaments, actualTournaments);
        verify(tournamentRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return tournaments filtered by tournamentResultId")
    void findByFilterWhenFilteredByTournamentResultId() {
        Long tournamentResultId = 1L;
        List<TournamentEntity> tournamentEntities = List.of(
            new TournamentEntity(1L, "Tournament 1", null, null, null, null),
            new TournamentEntity(2L, "Tournament 2", null, null, null, null),
            new TournamentEntity(3L, "Tournament 3", null, null, null, null)
        );

        when(tournamentRepository.findAll(any(Specification.class)))
            .thenReturn(tournamentEntities);

        List<TournamentModel> result = tournamentService.findByFilter(
            null, null, null, tournamentResultId);

        assertEquals(3, result.size());
        assertEquals("Tournament 1", result.get(0).getName());
        assertEquals("Tournament 2", result.get(1).getName());
        assertEquals("Tournament 3", result.get(2).getName());

        verify(tournamentRepository, times(1))
            .findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should throw an exception when the id does not exist")
    void findByIdWhenIdDoesNotExistThenThrowException() {
        Long id = 1L;
        Optional<TournamentEntity> emptyOptional = Optional.empty();

        when(tournamentRepository.findById(id)).thenReturn(emptyOptional);

        assertThrows(TournamentNofFoundException.class, () -> {
            tournamentService.findById(id);
        });

        verify(tournamentRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return the tournament when the id exists")
    void findByIdWhenIdExists() {
        Long id = 1L;
        TournamentEntity tournamentEntity = new TournamentEntity();
        tournamentEntity.setId(id);
        tournamentEntity.setName("Test Tournament");

        when(tournamentRepository.findById(id)).thenReturn(Optional.of(tournamentEntity));

        TournamentModel result = tournamentService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test Tournament", result.getName());

        verify(tournamentRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should save the tournament entity and return the corresponding model")
    void saveTournamentEntity() {
        TournamentEntity tournamentEntity = new TournamentEntity();
        tournamentEntity.setId(1L);
        tournamentEntity.setName("Test Tournament");

        TournamentEntity savedTournamentEntity = new TournamentEntity();
        savedTournamentEntity.setId(1L);
        savedTournamentEntity.setName("Test Tournament");

        when(tournamentRepository.save(tournamentEntity)).thenReturn(
            savedTournamentEntity);

        TournamentModel result = tournamentService.save(tournamentEntity);

        assertNotNull(result);
        assertEquals(savedTournamentEntity.getId(), result.getId());
        assertEquals(savedTournamentEntity.getName(), result.getName());

        verify(tournamentRepository, times(1)).save(tournamentEntity);
    }
}