package com.ss.portal.round.service;

import com.ss.portal.match.exception.MatchNotFoundException;
import com.ss.portal.round.entity.RoundEntity;
import com.ss.portal.round.exception.RoundNotFoundException;
import com.ss.portal.round.model.RoundModel;
import com.ss.portal.round.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ss.portal.round.model.RoundModel.toModel;
import static com.ss.portal.round.repository.RoundRepository.Specs.byRoundResultId;
import static com.ss.portal.shared.repository.SharedRepository.Specs.*;

@Service
public class RoundService {

    private final RoundRepository roundRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    public RoundModel save(RoundEntity round) {
        var newRound = roundRepository.save(round);

        return toModel(newRound);
    }

    public RoundModel findById(Long id) {
        var round = roundRepository.findById(id);

        return toModel(round.orElseThrow(() -> new RoundNotFoundException(id)));
    }

    public List<RoundModel> findByFilter(Long matchId, Long teamId, Long gameId,
                                         Long roundResultId) {
        return toModel(
            roundRepository.findAll(
                createSpecification(matchId, teamId, gameId, roundResultId)));

    }

    public Specification<RoundEntity> createSpecification(Long matchId, Long teamId,
                                                          Long gameId,
                                                          Long roundResultId) {
        Specification<RoundEntity> spec = Specification.where(null);

        if (matchId != null) spec = spec.and(byMatchId(matchId));
        if (teamId != null) spec = spec.and(byTeamId(teamId));
        if (gameId != null) spec = spec.and(byGameId(gameId));
        if (roundResultId != null) spec = spec.and(byRoundResultId(roundResultId));

        return spec;
    }

    public void updateById(Long id, RoundEntity round) {
        var optionalEntity = roundRepository.findById(id);

        if (optionalEntity.isPresent()) {
            var existingEntity = optionalEntity.get();

            existingEntity.setId(round.getId());
            existingEntity.setName(round.getName());
            existingEntity.setMatches(round.getMatches());
            existingEntity.setTeams(round.getTeams());
            existingEntity.setGames(round.getGames());
            existingEntity.setRoundResults(round.getRoundResults());

            roundRepository.save(existingEntity);
        } else {
            throw new MatchNotFoundException(id);
        }
    }

    public void deleteById(Long id) {
        roundRepository.deleteById(id);
    }

    public void deleteByMatchId(Long matchId) {
        roundRepository.deleteByMatchId(matchId);
    }

    public void deleteByTeamId(Long teamId) {
        roundRepository.deleteByTeamId(teamId);
    }

    public void deleteByGameId(Long gameId) {
        roundRepository.deleteByGameId(gameId);
    }

}
