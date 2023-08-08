package com.ss.portal.match.service;

import com.ss.portal.match.entity.MatchResultEntity;
import com.ss.portal.match.exception.MatchResultNotFoundException;
import com.ss.portal.match.model.MatchResultModel;
import com.ss.portal.match.repository.MatchResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ss.portal.match.repository.MatchResultRepository.Specs.byMatchId;
import static com.ss.portal.match.repository.MatchResultRepository.Specs.byTeamId;

@Service
public class MatchResultService {
    private final MatchResultRepository matchResultRepository;

    @Autowired
    public MatchResultService(MatchResultRepository matchResultRepository) {
        this.matchResultRepository = matchResultRepository;
    }

    public MatchResultModel save(MatchResultEntity matchResult) {
        return MatchResultModel.toModel(matchResultRepository.save(matchResult));
    }

    public List<MatchResultModel> findAll() {
        var matchResults = matchResultRepository.findAll();

        return MatchResultModel.toModel(matchResults);
    }


    public List<MatchResultModel> findByFilter(Long teamId, Long matchId) {
        if (matchId == null && teamId == null) return findAll();
        else if (matchId == null && teamId != null) return findByTeamId(teamId);
        else if (matchId != null && teamId == null) return findByMatchId(matchId);
        else return findByTeamIdAndMatchId(teamId, matchId);
    }

    public MatchResultModel findById(Long id) {
        var matchResult = matchResultRepository.findById(id);

        return MatchResultModel.toModel(matchResult.isPresent() ? matchResult.get() :
            null);
    }

    public List<MatchResultModel> findByTeamId(Long teamId) {
        var matchResult = matchResultRepository.findAll(byTeamId(teamId));

        return MatchResultModel.toModel(matchResult);
    }

    public List<MatchResultModel> findByMatchId(Long matchId) {
        var matchResult = matchResultRepository.findAll(byMatchId(matchId));

        return MatchResultModel.toModel(matchResult);
    }

    public List<MatchResultModel> findByTeamIdAndMatchId(Long teamId, Long matchId) {
        var matchResult =
            matchResultRepository.findAll(byTeamId(teamId).and(byMatchId(matchId)));

        return MatchResultModel.toModel(matchResult);
    }

    public void updateById(Long id, MatchResultEntity updateEntity) {
        var optionalEntity = matchResultRepository.findById(id);

        if (optionalEntity.isPresent()) {
            var existingEntity = optionalEntity.get();

            existingEntity.setScore(updateEntity.getScore());
            existingEntity.setPoint(updateEntity.getPoint());
            existingEntity.setTeam(updateEntity.getTeam());
            existingEntity.setMatch(updateEntity.getMatch());

            matchResultRepository.save(existingEntity);
        } else {
            throw new MatchResultNotFoundException(id);
        }
    }

    public void deleteById(Long id) {
        matchResultRepository.deleteById(id);
    }

    public void deleteByTeamId(Long teamId) {
        matchResultRepository.deleteByTeamId(teamId);
    }

    public void deleteByMatchId(Long matchId) {
        matchResultRepository.deleteByMatchId(matchId);
    }
}
