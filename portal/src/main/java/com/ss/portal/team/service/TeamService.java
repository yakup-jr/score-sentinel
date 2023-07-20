package com.ss.portal.team.service;

import com.ss.portal.team.entity.TeamEntity;
import com.ss.portal.team.exception.TeamAlreadyExistException;
import com.ss.portal.team.exception.TeamNotFoundException;
import com.ss.portal.team.model.TeamModel;
import com.ss.portal.team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ss.portal.team.repository.TeamRepository.Specs.byGameId;
import static com.ss.portal.team.repository.TeamRepository.Specs.byUserId;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamModel save(TeamEntity team) throws TeamAlreadyExistException {
        if (findByName(team.getName()) == null)
            return TeamModel.toModel(teamRepository.save(team));
        throw new TeamAlreadyExistException(team.getName());
    }

    public TeamModel findById(Long id) {
        Optional<TeamEntity> team = teamRepository.findById(id);

        return TeamModel.toModel(team.isPresent() ? team.get() : null);
    }

    public TeamModel findByName(String name) {
        System.out.print("Name: " + name);
        Optional<TeamEntity> team = teamRepository.findByName(name);

        return team.map(TeamModel::toModel).orElse(null);
    }

    public List<TeamModel> findByFilter(Long userId, Long gameId) {
        if (userId != null && gameId != null)
            return findByUserIdAndGameId(userId, gameId);
        else if (userId == null && gameId != null) return findByGameId(gameId);
        else if (gameId == null && userId != null) return findByUserId(userId);
        return findAll();
    }

    public List<TeamModel> findByUserId(Long userId) {
        return TeamModel.toModel(teamRepository.findAll(byUserId(userId)));
    }

    public List<TeamModel> findByGameId(Long gameId) {
        return TeamModel.toModel(teamRepository.findAll(byGameId(gameId)));
    }

    public List<TeamModel> findAll() {
        return TeamModel.toModel(teamRepository.findAll());
    }

    public List<TeamModel> findByUserIdAndGameId(Long userId, Long gameId) {
        return TeamModel.toModel(
            teamRepository.findAll(byUserId(userId).and(byGameId(gameId))));
    }

    public TeamModel updateNameById(Long id, TeamEntity team) {
        teamRepository.updateNameById(team.getName(), id);

        Optional<TeamEntity> updatedTeam = teamRepository.findById(team.getId());

        return TeamModel.toModel(updatedTeam.isPresent() ? updatedTeam.get() : null);
    }

    public void deleteById(Long id) throws TeamNotFoundException {
        if (this.findById(id) == null)
            throw new TeamNotFoundException(id);
        teamRepository.deleteById(id);
    }

    public void deleteByName(String name) throws TeamNotFoundException {
        if (this.findByName(name) == null)
            throw new TeamNotFoundException(name);
        teamRepository.deleteByName(name);
    }
}
