package com.ss.portal.round.repository;

import com.ss.portal.team.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoundResultRepositoryTest {

    @Mock
    private RoundResultRepository roundResultRepository;

    @Mock
    private TeamRepository teamRepository;

    @Test
    @DisplayName("Should not delete any round results when the team ID does not exist")
    void deleteByTeamIdWhenTeamIdDoesNotExist() {
        Long teamId = 1L;

        verify(roundResultRepository, never()).deleteByTeamId(teamId);
    }

    @Test
    @DisplayName("Should delete all round results associated with a specific team ID")
    void deleteByTeamId() {
        Long teamId = 1L;

        roundResultRepository.deleteByTeamId(teamId);

        verify(roundResultRepository, times(1)).deleteByTeamId(teamId);
    }

    @Test
    @DisplayName("Should not delete anything when the round id does not exist")
    void deleteByRoundIdWhenRoundIdDoesNotExist() {
        Long roundId = 1L;

        verify(roundResultRepository, never()).deleteByRoundId(roundId);
    }

    @Test
    @DisplayName("Should delete the round result by round id")
    void deleteByRoundId() {
        Long roundId = 1L;

        roundResultRepository.deleteByRoundId(roundId);

        verify(roundResultRepository, times(1)).deleteByRoundId(roundId);
    }
}