package com.ss.portal.round.repository;

import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.round.entity.RoundResultEntity;
import com.ss.portal.round.service.RoundService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.ss.portal.round.repository.RoundRepository.Specs.byRoundResultId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoundRepositoryTest {

    @Mock
    private RoundRepository roundRepository;

    @InjectMocks
    private RoundService roundService;

    @Test
    @DisplayName("Should delete the round entities associated with the given team id")
    void deleteByTeamId() {
        Long teamId = 1L;
        roundRepository.deleteByTeamId(teamId);

        verify(roundRepository, times(1)).deleteByTeamId(teamId);
    }

    @Test
    @DisplayName(
        "Should not delete any round entities when there are no matches associated with the given team id")
    void deleteByTeamIdWhenNoMatchesAssociated() {
        Long teamId = 1L;
        List<RoundEntity> rounds =
            List.of(new RoundEntity(1L, "Round 1", null, null, null, null),
                new RoundEntity(2L, "Round 2", null, null, null, null),
                new RoundEntity(3L, "Round 3", null, null, null, null));

        roundService.deleteByTeamId(teamId);

        verify(roundRepository, times(0)).delete(any(RoundEntity.class));
    }

    @Test
    @DisplayName("Should delete the round entity by game id")
    void deleteByGameId() {
        Long gameId = 1L;
        roundRepository.deleteByGameId(gameId);

        verify(roundRepository, times(1)).deleteByGameId(gameId);
    }

    @Test
    @DisplayName("Should not delete any round entity when game id does not exist")
    void deleteByGameIdWhenIdDoesNotExist() {
        Long gameId = 1L;
        doNothing().when(roundRepository).deleteByGameId(gameId);

        roundRepository.deleteByGameId(gameId);

        verify(roundRepository, times(1)).deleteByGameId(gameId);
    }

    @Test
    @DisplayName("Should delete the round entity by match id")
    void deleteByMatchId() {
        Long matchId = 1L;
        doNothing().when(roundRepository).deleteByMatchId(matchId);

        roundRepository.deleteByMatchId(matchId);

        verify(roundRepository, times(1)).deleteByMatchId(matchId);
    }

    @Test
    @DisplayName("Should not delete any round entity when the match id does not exist")
    void deleteByMatchIdWhenMatchIdDoesNotExist() {
        Long matchId = 1L;

        roundRepository.deleteByMatchId(matchId);

        verify(roundRepository, times(1)).deleteByMatchId(matchId);
        verify(roundRepository, never()).deleteById(anyLong());
    }
}

@ExtendWith(MockitoExtension.class)
class SpecsTest {

    @Mock
    private RoundRepository roundRepository;

    @InjectMocks
    private RoundService roundService;

    @Test
    @DisplayName("Should return null when an invalid roundResultId is provided")
    void byRoundResultIdWithInvalidId() {
        Long roundResultId = 100L;
        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setId(1L);
        roundEntity.setName("Round 1");

        var result = roundService.findByFilter(null, null, null, roundResultId);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName(
        "Should return the correct RoundEntity when a valid roundResultId is provided")
    void byRoundResultIdWithValidId() {
        Long roundResultId = 1L;
        RoundResultEntity roundResultEntity = new RoundResultEntity();
        roundResultEntity.setId(roundResultId);
        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setRoundResults(List.of(roundResultEntity));

        when(roundRepository.findOne(
            byRoundResultId(roundResultId))).thenReturn(
            Optional.of(roundEntity));

        var result = roundRepository.findOne(byRoundResultId(roundResultId));

        assertTrue(result.isPresent());
        assertEquals(roundEntity, result.get());

        verify(roundRepository, times(1)).findOne(
            byRoundResultId(roundResultId));
    }

}