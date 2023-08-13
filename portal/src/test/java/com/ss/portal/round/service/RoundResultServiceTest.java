package com.ss.portal.round.service;

import com.ss.portal.match.exception.MatchNotFoundException;
import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.round.entity.RoundResultEntity;
import com.ss.portal.round.exception.RoundResultNotFoundException;
import com.ss.portal.round.model.RoundResultModel;
import com.ss.portal.round.repository.RoundResultRepository;
import com.ss.portal.team.entity.TeamEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ss.portal.round.model.RoundResultModel.toModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoundResultServiceTest {

    @Mock
    private RoundResultRepository roundResultRepository;

    @InjectMocks
    private RoundResultService roundResultService;

    @Test
    @DisplayName("Should delete the round result by id")
    void deleteRoundResultById() {
        Long id = 1L;
        // Create a RoundResultEntity with the given id
        RoundResultEntity roundResult = new RoundResultEntity();
        roundResult.setId(id);

        // Call the deleteById method of roundResultService
        roundResultService.deleteById(id);

        // Verify that the deleteById method of roundResultRepository was called with the correct id
        verify(roundResultRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should delete all round results by team id")
    void deleteByTeamId() {
        Long teamId = 1L;

        roundResultService.deleteByTeamId(teamId);

        verify(roundResultRepository, times(1)).deleteByTeamId(teamId);
    }

    @Test
    @DisplayName(
        "Should not throw an exception when no round results found for the given team id")
    void deleteByTeamIdWhenNoRoundResultsFound() {
        Long teamId = 1L;

        assertDoesNotThrow(() -> roundResultService.deleteByTeamId(teamId));

        verify(roundResultRepository, times(1)).deleteByTeamId(teamId);
    }

    @Test
    @DisplayName("Should delete the round result by round id")
    void deleteByRoundId() {
        Long roundId = 1L;

        roundResultService.deleteByRoundId(roundId);

        verify(roundResultRepository, times(1)).deleteByRoundId(roundId);
    }

    @Test
    @DisplayName(
        "Should return an empty list when no RoundResultModels match the filter criteria")
    void findByFilterReturnsEmptyListWhenNoMatch() {
        Long teamId = 1L;
        Long userId = 1L;

        when(roundResultRepository.findAll(any(Specification.class))).thenReturn(
            Collections.emptyList());

        List<RoundResultModel> result = roundResultService.findByFilter(teamId, userId);

        assertTrue(result.isEmpty());
        verify(roundResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName(
        "Should return a list of RoundResultModels filtered by both teamId and userId")
    void findByFilterByBothTeamIdAndUserId() {
        Long teamId = 1L;
        Long userId = 2L;

        List<RoundResultEntity> roundResultEntities = List.of(
            new RoundResultEntity(1L, 10, 5.0f, new TeamEntity(), new RoundEntity()),
            new RoundResultEntity(2L, 15, 7.5f, new TeamEntity(), new RoundEntity())
        );

        when(roundResultRepository.findAll(any(Specification.class))).thenReturn(
            roundResultEntities);

        List<RoundResultModel> result = roundResultService.findByFilter(teamId, userId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(10, result.get(0).getScore());
        assertEquals(5.0f, result.get(0).getPoints());
        assertNotNull(result.get(0).getTeam());
        assertNotNull(result.get(0).getRound());
        assertEquals(2L, result.get(1).getId());
        assertEquals(15, result.get(1).getScore());
        assertEquals(7.5f, result.get(1).getPoints());
        assertNotNull(result.get(1).getTeam());
        assertNotNull(result.get(1).getRound());

        verify(roundResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return a list of RoundResultModels filtered by teamId only")
    void findByFilterByTeamIdOnly() {
        Long teamId = 1L;
        Long userId = null;

        List<RoundResultEntity> roundResultEntities = new ArrayList<>();
        RoundResultEntity roundResultEntity1 = new RoundResultEntity();
        roundResultEntity1.setId(1L);
        roundResultEntity1.setScore(10);
        roundResultEntity1.setPoints(5.0f);
        roundResultEntity1.setTeam(new TeamEntity());
        roundResultEntity1.setRound(new RoundEntity());
        roundResultEntities.add(roundResultEntity1);

        when(roundResultRepository.findAll(any(Specification.class))).thenReturn(
            roundResultEntities);

        List<RoundResultModel> roundResultModels =
            roundResultService.findByFilter(teamId, userId);

        assertEquals(1, roundResultModels.size());
        assertEquals(roundResultEntity1.getId(), roundResultModels.get(0).getId());
        assertEquals(roundResultEntity1.getScore(), roundResultModels.get(0).getScore());
        assertEquals(roundResultEntity1.getPoints(),
            roundResultModels.get(0).getPoints());
        assertEquals(roundResultEntity1.getTeam(), roundResultModels.get(0).getTeam());
        assertEquals(roundResultEntity1.getRound(), roundResultModels.get(0).getRound());

        verify(roundResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return a list of RoundResultModels filtered by userId only")
    void findByFilterByUserIdOnly() {
        Long userId = 1L;
        Long teamId = null;

        List<RoundResultEntity> roundResultEntities = new ArrayList<>();
        RoundResultEntity roundResultEntity1 = new RoundResultEntity();
        roundResultEntity1.setId(1L);
        roundResultEntity1.setScore(80);
        roundResultEntity1.setPoints(8.5f);
        roundResultEntity1.setTeam(new TeamEntity());
        roundResultEntity1.setRound(new RoundEntity());
        roundResultEntities.add(roundResultEntity1);

        RoundResultEntity roundResultEntity2 = new RoundResultEntity();
        roundResultEntity2.setId(2L);
        roundResultEntity2.setScore(90);
        roundResultEntity2.setPoints(9.0f);
        roundResultEntity2.setTeam(new TeamEntity());
        roundResultEntity2.setRound(new RoundEntity());
        roundResultEntities.add(roundResultEntity2);

        when(roundResultRepository.findAll(any(Specification.class))).thenReturn(
            roundResultEntities);

        List<RoundResultModel> roundResultModels =
            roundResultService.findByFilter(teamId, userId);

        assertEquals(2, roundResultModels.size());
        assertEquals(1L, roundResultModels.get(0).getId());
        assertEquals(80, roundResultModels.get(0).getScore());
        assertEquals(8.5f, roundResultModels.get(0).getPoints());
        assertNotNull(roundResultModels.get(0).getTeam());
        assertNotNull(roundResultModels.get(0).getRound());
        assertEquals(2L, roundResultModels.get(1).getId());
        assertEquals(90, roundResultModels.get(1).getScore());
        assertEquals(9.0f, roundResultModels.get(1).getPoints());
        assertNotNull(roundResultModels.get(1).getTeam());
        assertNotNull(roundResultModels.get(1).getRound());

        verify(roundResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should update the round result by id when the id exists")
    void updateByIdWhenIdExists() {
        Long id = 1L;
        RoundResultEntity roundResult = new RoundResultEntity();
        roundResult.setId(id);
        roundResult.setScore(100);
        roundResult.setPoints(9.5f);
        TeamEntity team = new TeamEntity();
        team.setId(1L);
        roundResult.setTeam(team);
        RoundEntity round = new RoundEntity();
        round.setId(1L);
        roundResult.setRound(round);

        when(roundResultRepository.findById(id)).thenReturn(Optional.of(roundResult));

        roundResultService.updateById(id, roundResult);

        verify(roundResultRepository, times(1)).save(roundResult);
    }

    @Test
    @DisplayName("Should throw MatchNotFoundException when the id does not exist")
    void updateByIdWhenIdDoesNotExistThenThrowMatchNotFoundException() {
        Long id = 1L;
        RoundResultEntity roundResultEntity = new RoundResultEntity();
        roundResultEntity.setId(id);
        roundResultEntity.setScore(10);
        roundResultEntity.setPoints(5.0f);
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(1L);
        roundResultEntity.setTeam(teamEntity);
        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setId(1L);
        roundResultEntity.setRound(roundEntity);

        when(roundResultRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(MatchNotFoundException.class,
            () -> roundResultService.updateById(id, roundResultEntity));

        verify(roundResultRepository, times(1)).findById(id);
        verify(roundResultRepository, never()).save(any(RoundResultEntity.class));
    }

    @Test
    @DisplayName("Should throw RoundResultNotFoundException when the id does not exist")
    void findByIdWhenIdDoesNotExistThenThrowException() {
        Long id = 1L;
        when(roundResultRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RoundResultNotFoundException.class,
            () -> roundResultService.findById(id));

        verify(roundResultRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return the round result when the id exists")
    void findByIdWhenIdExists() {
        Long id = 1L;
        RoundResultEntity roundResultEntity = new RoundResultEntity();
        roundResultEntity.setId(id);
        RoundResultModel expectedRoundResultModel =
            toModel(roundResultEntity);

        when(roundResultRepository.findById(id)).thenReturn(
            Optional.of(roundResultEntity));

        RoundResultModel actualRoundResultModel = roundResultService.findById(id);

        assertEquals(expectedRoundResultModel, actualRoundResultModel);
        verify(roundResultRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should save the round result and return the saved model")
    void saveRoundResultAndReturnModel() {
        RoundResultEntity roundResultEntity = new RoundResultEntity();
        roundResultEntity.setId(1L);
        roundResultEntity.setScore(100);
        roundResultEntity.setPoints(9.5f);
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(1L);
        roundResultEntity.setTeam(teamEntity);
        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setId(1L);
        roundResultEntity.setRound(roundEntity);

        when(roundResultRepository.save(roundResultEntity)).thenReturn(roundResultEntity);

        RoundResultModel result = roundResultService.save(roundResultEntity);

        assertNotNull(result);
        assertEquals(roundResultEntity.getId(), result.getId());
        assertEquals(roundResultEntity.getScore(), result.getScore());
        assertEquals(roundResultEntity.getPoints(), result.getPoints());
        assertEquals(roundResultEntity.getTeam(), result.getTeam());
        assertEquals(roundResultEntity.getRound(), result.getRound());

        verify(roundResultRepository, times(1)).save(roundResultEntity);
    }
}