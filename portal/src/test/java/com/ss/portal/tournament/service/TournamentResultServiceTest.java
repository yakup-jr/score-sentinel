package com.ss.portal.tournament.service;

import com.ss.portal.team.entity.TeamEntity;
import com.ss.portal.tournament.entity.TournamentEntity;
import com.ss.portal.tournament.entity.TournamentResultEntity;
import com.ss.portal.tournament.exception.TournamentResultNotFoundException;
import com.ss.portal.tournament.model.TournamentResultModel;
import com.ss.portal.tournament.repository.TournamentResultRepository;
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

import static com.ss.portal.shared.repository.SharedRepository.Specs.byTeamId;
import static com.ss.portal.tournament.repository.TournamentResultRepository.Specs.byTournamentId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentResultServiceTest {

    @Mock
    private TournamentResultRepository tournamentResultRepository;

    @InjectMocks
    private TournamentResultService tournamentResultService;


    @Test
    @DisplayName("Should delete all tournament results by given tournament id")
    void deleteByTournamentId() {
        Long tournamentId = 1L;

        tournamentResultService.deleteByTournamentId(tournamentId);

        verify(tournamentResultRepository, times(1)).deleteByTournamentId(tournamentId);
    }

    @Test
    @DisplayName(
        "Should not throw an exception when no tournament results found by given tournament id")
    void deleteByTournamentIdWhenNoResultsFound() {
        Long tournamentId = 1L;

        // Mock the repository behavior
        when(tournamentResultRepository.findAll(byTournamentId(tournamentId))).thenReturn(
            new ArrayList<>());

        // Call the method under test
        assertDoesNotThrow(
            () -> tournamentResultService.deleteByTournamentId(tournamentId));

        // Verify that the repository method was called
        verify(tournamentResultRepository, times(1)).deleteByTournamentId(tournamentId);
    }

    @Test
    @DisplayName("Should not throw an exception when deleting by non-existing team id")
    void deleteByNonExistingTeamId() {
        Long teamId = 1L;

        doNothing().when(tournamentResultRepository).deleteByTeamId(teamId);

        assertDoesNotThrow(() -> tournamentResultService.deleteByTeamId(teamId));

        verify(tournamentResultRepository, times(1)).deleteByTeamId(teamId);
    }

    @Test
    @DisplayName("Should delete the tournament results by team id")
    void deleteByTeamId() {
        Long teamId = 1L;

        // Mocking the repository method
        doNothing().when(tournamentResultRepository).deleteByTeamId(teamId);

        // Calling the method under test
        tournamentResultService.deleteByTeamId(teamId);

        // Verifying that the repository method was called once
        verify(tournamentResultRepository, times(1)).deleteByTeamId(teamId);
    }

    @Test
    @DisplayName("Should delete the tournament result by id")
    void deleteTournamentResultById() {
        Long id = 1L;

        // Mocking the repository behavior
        doNothing().when(tournamentResultRepository).deleteById(id);

        // Calling the method under test
        tournamentResultService.deleteById(id);

        // Verifying that the repository method was called with the correct argument
        verify(tournamentResultRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName(
        "Should throw an exception when trying to delete a non-existing tournament result by id")
    void deleteNonExistingTournamentResultByIdThenThrowException() {
        Long id = 1L;
        when(tournamentResultRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TournamentResultNotFoundException.class,
            () -> tournamentResultService.deleteById(id),
            "Expected TournamentResultNotFoundException to be thrown");

        verify(tournamentResultRepository, times(1)).findById(id);
        verify(tournamentResultRepository, never()).deleteById(any());
    }

    /*@Test
    @DisplayName(
        "Should return a specification with no conditions when both teamId and tournamentId are null")
    void createSpecificationWhenTeamIdAndTournamentIdAreNull() {
        Long teamId = null;
        Long tournamentId = null;

        Specification<TournamentResultEntity> spec =
            tournamentResultService.createSpecification(teamId, tournamentId);

        assertNull(spec.getPredicate());
    }*/

    /*@Test
    @DisplayName(
        "Should return a specification with only tournamentId when teamId is null")
    void createSpecificationWhenTeamIdIsNull() {
        Long teamId = null;
        Long tournamentId = 1L;

        Specification<TournamentResultEntity> expectedSpec =
            Specification.<TournamentResultEntity>where(null)
            .and(tournamentResultService.findByFilter(null, tournamentId));

        Specification<TournamentResultEntity> actualSpec =
            tournamentResultService.createSpecification(teamId, tournamentId);

        assertEquals(expectedSpec, actualSpec);
}*/

    @Test
    @DisplayName(
        "Should return a specification with only teamId when tournamentId is null")
    void createSpecificationWhenTournamentIdIsNull() {
        Long teamId = 1L;
        Long tournamentId = null;

        Specification<TournamentResultEntity> expectedSpec =
            Specification.<TournamentResultEntity>where(null).and(byTeamId(teamId));

        Specification<TournamentResultEntity> actualSpec =
            tournamentResultService.createSpecification(teamId, tournamentId);

        assertEquals(expectedSpec, actualSpec);
    }

    /*@Test
    @DisplayName(
        "Should return a specification with teamId and tournamentId when both are not null")
    void createSpecificationWhenTeamIdAndTournamentIdAreNotNull() {
        Long teamId = 1L;
        Long tournamentId = 2L;

        Specification<TournamentResultEntity> expectedSpec = Specification
            .where(byTeamId(teamId))
            .and(byTournamentId(tournamentId));

        Specification<TournamentResultEntity> actualSpec =
            tournamentResultService.createSpecification(teamId, tournamentId);

        assertEquals(expectedSpec, actualSpec);
    }*/

    @Test
    @DisplayName(
        "Should throw an exception when trying to update the tournament result by id that does not exist")
    void updateByIdWhenIdDoesNotExistThenThrowException() {
        Long id = 1L;
        TournamentResultEntity tournamentResult = new TournamentResultEntity();
        tournamentResult.setId(id);

        when(tournamentResultRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(TournamentResultNotFoundException.class, () -> {
            tournamentResultService.updateById(id, tournamentResult);
        });

        verify(tournamentResultRepository, times(1)).findById(id);
        verify(tournamentResultRepository, never()).save(
            any(TournamentResultEntity.class));
    }

    @Test
    @DisplayName("Should update the tournament result by id when the id exists")
    void updateByIdWhenIdExists() {
        Long id = 1L;
        TournamentResultEntity existingEntity = new TournamentResultEntity();
        existingEntity.setId(id);
        existingEntity.setScore(10);
        existingEntity.setPoints(5.0f);
        existingEntity.setTeam(new TeamEntity());
        existingEntity.setTournament(new TournamentEntity());

        TournamentResultEntity updatedEntity = new TournamentResultEntity();
        updatedEntity.setId(id);
        updatedEntity.setScore(15);
        updatedEntity.setPoints(7.5f);
        updatedEntity.setTeam(new TeamEntity());
        updatedEntity.setTournament(new TournamentEntity());

        when(tournamentResultRepository.findById(id)).thenReturn(
            Optional.of(existingEntity));
        when(tournamentResultRepository.save(
            any(TournamentResultEntity.class))).thenReturn(updatedEntity);

        tournamentResultService.updateById(id, updatedEntity);

        verify(tournamentResultRepository, times(1)).findById(id);
        verify(tournamentResultRepository, times(1)).save(existingEntity);
        assertEquals(updatedEntity.getScore(), existingEntity.getScore());
        assertEquals(updatedEntity.getPoints(), existingEntity.getPoints());
        assertEquals(updatedEntity.getTeam(), existingEntity.getTeam());
        assertEquals(updatedEntity.getTournament(), existingEntity.getTournament());
    }

    @Test
    @DisplayName("Should return empty list when no tournament results match the filter")
    void findByFilterWhenNoResultsMatchTheFilter() {
        Long teamId = 1L;
        Long tournamentId = 1L;
        List<TournamentResultEntity> tournamentResults = new ArrayList<>();
        when(tournamentResultRepository.findAll(any(Specification.class))).thenReturn(
            tournamentResults);

        List<TournamentResultModel> result =
            tournamentResultService.findByFilter(teamId, tournamentId);

        assertTrue(result.isEmpty());
        verify(tournamentResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return all tournament results when no filter is provided")
    void findByFilterWhenNoFilterIsProvided() {
        Long teamId = null;
        Long tournamentId = null;
        List<TournamentResultEntity> tournamentResults = new ArrayList<>();
        tournamentResults.add(new TournamentResultEntity(1L, 10, 5.0f, null, null));
        tournamentResults.add(new TournamentResultEntity(2L, 15, 7.5f, null, null));
        when(tournamentResultRepository.findAll(any(Specification.class))).thenReturn(
            tournamentResults);

        List<TournamentResultModel> result =
            tournamentResultService.findByFilter(teamId, tournamentId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(10, result.get(0).getScore());
        assertEquals(5.0f, result.get(0).getPoints());
        assertEquals(2L, result.get(1).getId());
        assertEquals(15, result.get(1).getScore());
        assertEquals(7.5f, result.get(1).getPoints());

        verify(tournamentResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return tournament results filtered by tournamentId only")
    void findByFilterWhenFilteredByTournamentIdOnly() {
        Long tournamentId = 1L;
        Long teamId = null;

        List<TournamentResultEntity> tournamentResults = new ArrayList<>();
        tournamentResults.add(new TournamentResultEntity(1L, 10, 5.0f, null, null));
        tournamentResults.add(new TournamentResultEntity(2L, 15, 7.5f, null, null));

        when(tournamentResultRepository.findAll(any(Specification.class)))
            .thenReturn(tournamentResults);

        List<TournamentResultModel> result =
            tournamentResultService.findByFilter(teamId, tournamentId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(10, result.get(0).getScore());
        assertEquals(5.0f, result.get(0).getPoints());
        assertNull(result.get(0).getTeam());
        assertNull(result.get(0).getTournament());
        assertEquals(2L, result.get(1).getId());
        assertEquals(15, result.get(1).getScore());
        assertEquals(7.5f, result.get(1).getPoints());
        assertNull(result.get(1).getTeam());
        assertNull(result.get(1).getTournament());

        verify(tournamentResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should return tournament results filtered by teamId only")
    void findByFilterWhenFilteredByTeamIdOnly() {
        Long teamId = 1L;
        Long tournamentId = null;

        List<TournamentResultEntity> tournamentResults = new ArrayList<>();
        tournamentResults.add(new TournamentResultEntity(1L, 10, 5.0f, null, null));
        tournamentResults.add(new TournamentResultEntity(2L, 15, 7.5f, null, null));

        when(tournamentResultRepository.findAll(any(Specification.class)))
            .thenReturn(tournamentResults);

        List<TournamentResultModel> result =
            tournamentResultService.findByFilter(teamId, tournamentId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(10, result.get(0).getScore());
        assertEquals(5.0f, result.get(0).getPoints());
        assertNull(result.get(0).getTeam());
        assertNull(result.get(0).getTournament());
        assertEquals(2L, result.get(1).getId());
        assertEquals(15, result.get(1).getScore());
        assertEquals(7.5f, result.get(1).getPoints());
        assertNull(result.get(1).getTeam());
        assertNull(result.get(1).getTournament());

        verify(tournamentResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName(
        "Should return tournament results filtered by both teamId and tournamentId")
    void findByFilterWhenFilteredByBothTeamIdAndTournamentId() {
        Long teamId = 1L;
        Long tournamentId = 1L;
        List<TournamentResultEntity> tournamentResults = new ArrayList<>();
        tournamentResults.add(new TournamentResultEntity(1L, 10, 5.0f, new TeamEntity(),
            new TournamentEntity()));
        tournamentResults.add(new TournamentResultEntity(2L, 15, 7.5f, new TeamEntity(),
            new TournamentEntity()));
        when(tournamentResultRepository.findAll(any(Specification.class))).thenReturn(
            tournamentResults);

        List<TournamentResultModel> result =
            tournamentResultService.findByFilter(teamId, tournamentId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(10, result.get(0).getScore());
        assertEquals(5.0f, result.get(0).getPoints());
        assertNotNull(result.get(0).getTeam());
        assertNotNull(result.get(0).getTournament());
        assertEquals(2L, result.get(1).getId());
        assertEquals(15, result.get(1).getScore());
        assertEquals(7.5f, result.get(1).getPoints());
        assertNotNull(result.get(1).getTeam());
        assertNotNull(result.get(1).getTournament());

        verify(tournamentResultRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Should throw an exception when the id does not exist")
    void findByIdWhenIdDoesNotExistThenThrowException() {
        Long id = 1L;
        Optional<TournamentResultEntity> emptyOptional = Optional.empty();

        when(tournamentResultRepository.findById(id)).thenReturn(emptyOptional);

        assertThrows(TournamentResultNotFoundException.class, () -> {
            tournamentResultService.findById(id);
        });

        verify(tournamentResultRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return the tournament result when the id exists")
    void findByIdWhenIdExists() {
        Long id = 1L;
        TournamentResultEntity tournamentResultEntity = new TournamentResultEntity();
        tournamentResultEntity.setId(id);
        tournamentResultEntity.setScore(100);
        tournamentResultEntity.setPoints(10.5f);

        when(tournamentResultRepository.findById(id)).thenReturn(
            Optional.of(tournamentResultEntity));

        TournamentResultModel expectedModel = new TournamentResultModel();
        expectedModel.setId(id);
        expectedModel.setScore(100);
        expectedModel.setPoints(10.5f);

        TournamentResultModel actualModel = tournamentResultService.findById(id);

        assertEquals(expectedModel, actualModel);
        verify(tournamentResultRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw an exception when the tournament result is null")
    void saveTournamentResultWhenNullThenThrowException() {
        TournamentResultEntity tournamentResult = null;

        assertThrows(IllegalArgumentException.class, () -> {
            tournamentResultService.save(tournamentResult);
        });

        verify(tournamentResultRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should save the tournament result and return the saved model")
    void saveTournamentResult() {
        TournamentResultEntity tournamentResultEntity = new TournamentResultEntity();
        tournamentResultEntity.setId(1L);
        tournamentResultEntity.setScore(100);
        tournamentResultEntity.setPoints(9.5f);
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(1L);
        teamEntity.setName("Team 1");
        tournamentResultEntity.setTeam(teamEntity);
        TournamentEntity tournamentEntity = new TournamentEntity();
        tournamentEntity.setId(1L);
        tournamentEntity.setName("Tournament 1");
        tournamentResultEntity.setTournament(tournamentEntity);

        when(tournamentResultRepository.save(tournamentResultEntity)).thenReturn(
            tournamentResultEntity);

        TournamentResultModel savedModel =
            tournamentResultService.save(tournamentResultEntity);

        assertNotNull(savedModel);
        assertEquals(tournamentResultEntity.getId(), savedModel.getId());
        assertEquals(tournamentResultEntity.getScore(), savedModel.getScore());
        assertEquals(tournamentResultEntity.getPoints(), savedModel.getPoints());
        assertNotNull(savedModel.getTeam());
        assertEquals(teamEntity.getId(), savedModel.getTeam().getId());
        assertEquals(teamEntity.getName(), savedModel.getTeam().getName());
        assertNotNull(savedModel.getTournament());
        assertEquals(tournamentEntity.getId(), savedModel.getTournament().getId());
        assertEquals(tournamentEntity.getName(), savedModel.getTournament().getName());

        verify(tournamentResultRepository, times(1)).save(tournamentResultEntity);
    }
}