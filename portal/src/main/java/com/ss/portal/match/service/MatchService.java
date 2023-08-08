package com.ss.portal.match.service;

import com.ss.portal.match.entity.MatchEntity;
import com.ss.portal.match.exception.MatchNotFoundException;
import com.ss.portal.match.model.MatchModel;
import com.ss.portal.match.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ss.portal.match.model.MatchModel.toModel;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public MatchModel save(MatchEntity matchEntity) {
        return toModel(matchRepository.save(matchEntity));
    }

    public MatchModel findById(Long id) {
        return toModel(matchRepository.findById(id).orElse(null));
    }

    public List<MatchModel> findByFilter(Long teamId, Long gameId, Long matchResultId) {
        return toModel(matchRepository.findAll(
            createSpecification(teamId, gameId, matchResultId)));
    }

    public Specification<MatchEntity> createSpecification(Long teamId, Long gameId,
                                                          Long matchResultId) {
        Specification<MatchEntity> spec = Specification.where(null);

        if (teamId != null) {
            spec = spec.and(MatchRepository.Specs.byTeamId(teamId));
        }
        if (gameId != null) {
            spec = spec.and(MatchRepository.Specs.byGameId(gameId));
        }
        if (matchResultId != null) {
            spec = spec.and(MatchRepository.Specs.byMatchResultId(matchResultId));
        }

        return spec;
    }

    public void updateById(Long id, MatchEntity updateMatch) {
        var optionalEntity = matchRepository.findById(id);

        if (optionalEntity.isPresent()) {
            var existingEntity = optionalEntity.get();

            existingEntity.setGames(updateMatch.getGames());
            existingEntity.setScore(updateMatch.getScore());
            existingEntity.setMatchResults(updateMatch.getMatchResults());
            existingEntity.setName(updateMatch.getName());
            existingEntity.setTeams(updateMatch.getTeams());

            matchRepository.save(existingEntity);
        } else {
            throw new MatchNotFoundException(id);
        }
    }

    public void deleteById(Long id) {
        matchRepository.deleteById(id);
    }

    public void deleteByTeamId(Long teamId) {
        matchRepository.deleteByTeamId(teamId);
    }

    public void deleteByGameId(Long gameId) {
        matchRepository.deleteByGameId(gameId);
    }

    public void deleteByMatchResultId(Long matchResultId) {
        matchRepository.deleteByMatchResultId(matchResultId);
    }
}