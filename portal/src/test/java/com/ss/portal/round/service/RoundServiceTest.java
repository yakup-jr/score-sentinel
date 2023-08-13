package com.ss.portal.round.service;

import com.ss.portal.match.exception.MatchNotFoundException;
import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.round.exception.RoundNotFoundException;
import com.ss.portal.round.model.RoundModel;
import com.ss.portal.round.repository.RoundRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ss.portal.round.repository.RoundRepository.Specs.byRoundResultId;
import static com.ss.portal.shared.repository.SharedRepository.Specs.byGameId;
import static com.ss.portal.shared.repository.SharedRepository.Specs.byTeamId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoundServiceTest {

    @Mock
    private RoundRepository roundRepository;

    @InjectMocks
    private RoundService roundService;


    @Test
    @DisplayName("Should update the round by id when the round is present")
    void updateByIdWhenRoundIsPresent() {
        Long roundId = 1L;
        RoundEntity round = new RoundEntity();
        round.setId(roundId);
        round.setName("Round 1");

        RoundEntity existingRound = new RoundEntity();
        existingRound.setId(roundId);
        existingRound.setName("Round 1");

        when(roundRepository.findById(roundId)).thenReturn(Optional.of(existingRound));
        when(roundRepository.save(any(RoundEntity.class))).thenReturn(existingRound);

        roundService.updateById(roundId, round);

        verify(roundRepository, times(1)).findById(roundId);
        verify(roundRepository, times(1)).save(existingRound);

        assertEquals(round.getName(), existingRound.getName());
    }

    @Test
    @DisplayName("Should throw MatchNotFoundException when the round is not present")
    void updateByIdWhenRoundIsNotPresentThenThrowMatchNotFoundException() {
        Long roundId = 1L;
        RoundEntity round = new RoundEntity();
        round.setId(roundId);
        round.setName("Round 1");
        round.setMatches(new ArrayList<>());
        round.setTeams(new ArrayList<>());
        round.setGames(new ArrayList<>());
        round.setRoundResults(new ArrayList<>());

        when(roundRepository.findById(roundId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(MatchNotFoundException.class,
            () -> roundService.updateById(roundId, round));

        // Verify
        verify(roundRepository, times(1)).findById(roundId);
        verify(roundRepository, never()).save(any(RoundEntity.class));
    }

    @Test
    @DisplayName("Should create a specification with only gameId not null")
    void createSpecificationWithOnlyGameIdNotNull() {
        Long gameId = 1L;
        Long matchId = null;
        Long teamId = null;
        Long roundResultId = null;

        Specification<RoundEntity> expectedSpec =
            Specification.<RoundEntity>where(null).and(byGameId(gameId));

        Specification<RoundEntity> actualSpec =
            roundService.createSpecification(matchId, teamId, gameId, roundResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should create a specification with only matchId not null")
    void createSpecificationWithOnlyMatchIdNotNull() {
        Long roundResultId = 1L;

        Specification<RoundEntity> expectedSpec = (root, query, builder) -> {
            var joinRoundResult = root.join("roundResults");
            return builder.equal(joinRoundResult.get("id"), roundResultId);
        };

        Specification<RoundEntity> actualSpec = roundService.createSpecification(
            null, null, null, roundResultId);

        assertEquals(expectedSpec, actualSpec);

    }

    @Test
    @DisplayName("Should create a specification with only teamId not null")
    void createSpecificationWithOnlyTeamIdNotNull() {
        Long matchId = null;
        Long teamId = 1L;
        Long gameId = null;
        Long roundResultId = null;

        Specification<RoundEntity> expectedSpec =
            Specification.<RoundEntity>where(null).and(byTeamId(teamId));

        Specification<RoundEntity> actualSpec =
            roundService.createSpecification(matchId, teamId, gameId, roundResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should create a specification with only roundResultId not null")
    void createSpecificationWithOnlyRoundResultIdNotNull() {
        Long matchId = null;
        Long teamId = null;
        Long gameId = null;
        Long roundResultId = 1L;

        Specification<RoundEntity> expectedSpec = Specification.<RoundEntity>where(null)
            .and(byRoundResultId(roundResultId));

        Specification<RoundEntity> actualSpec =
            roundService.createSpecification(matchId, teamId, gameId, roundResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should create a specification with all parameters null")
    void createSpecificationWithAllParametersNull() {
        Long matchId = null;
        Long teamId = null;
        Long gameId = null;
        Long roundResultId = null;

        Specification<RoundEntity> expectedSpec = Specification.where(null);

        Specification<RoundEntity> actualSpec =
            roundService.createSpecification(matchId, teamId, gameId, roundResultId);

        assertEquals(expectedSpec, actualSpec);
    }

    @Test
    @DisplayName("Should return a list of rounds when only teamId filter is provided")
    void findByFilterWhenOnlyTeamIdIsProvided() {
        Long teamId = 1L;

        List<RoundEntity> roundEntities = new ArrayList<>();
        roundEntities.add(new RoundEntity(1L, "Round 1", null, null, null, null));
        roundEntities.add(new RoundEntity(2L, "Round 2", null, null, null, null));

        when(roundRepository.findAll(any(Specification.class))).thenReturn(roundEntities);

        List<RoundModel> result = roundService.findByFilter(null, teamId, null, null);

        assertEquals(2, result.size());
        assertEquals("Round 1", result.get(0).getName());
        assertEquals("Round 2", result.get(1).getName());

        verify(roundRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName(
        "Should throw RoundNotFoundException when the round with the given id does not exist")
    void findByIdWhenRoundDoesNotExistThenThrowException() {
        Long roundId = 1L;
        when(roundRepository.findById(roundId)).thenReturn(Optional.empty());

        // Act and Assert
        RoundNotFoundException exception =
            assertThrows(RoundNotFoundException.class,
                () -> roundService.findById(roundId));

        assertEquals("Round with id " + roundId + " not found", exception.getMessage());

        verify(roundRepository, times(1)).findById(roundId);
    }

    @Test
    @DisplayName("Should return the round model when the round with the given id exists")
    void findByIdWhenRoundExists() {
        Long roundId = 1L;
        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setId(roundId);
        roundEntity.setName("Round 1");

        when(roundRepository.findById(roundId)).thenReturn(Optional.of(roundEntity));

        RoundModel result = roundService.findById(roundId);

        assertNotNull(result);
        assertEquals(roundId, result.getId());
        assertEquals("Round 1", result.getName());

        verify(roundRepository, times(1)).findById(roundId);
    }

    @Test
    @DisplayName("Should save the round and return the model")
    void saveRoundAndReturnModel() {
        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setId(1L);
        roundEntity.setName("Round 1");

        when(roundRepository.save(roundEntity)).thenReturn(roundEntity);

        RoundModel result = roundService.save(roundEntity);

        assertNotNull(result);
        assertEquals(roundEntity.getId(), result.getId());
        assertEquals(roundEntity.getName(), result.getName());

        verify(roundRepository, times(1)).save(roundEntity);
    }
}