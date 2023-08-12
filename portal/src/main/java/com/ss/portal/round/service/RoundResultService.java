package com.ss.portal.round.service;

import com.ss.portal.match.exception.MatchNotFoundException;
import com.ss.portal.round.entity.RoundResultEntity;
import com.ss.portal.round.exception.RoundResultNotFoundException;
import com.ss.portal.round.model.RoundResultModel;
import com.ss.portal.round.repository.RoundResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ss.portal.round.model.RoundResultModel.toModel;
import static com.ss.portal.shared.repository.SharedRepository.Specs.byTeamId;
import static com.ss.portal.shared.repository.SharedRepository.Specs.byUserId;

@Service
public class RoundResultService {

    private final RoundResultRepository roundResultRepository;

    @Autowired
    RoundResultService(RoundResultRepository roundResultRepository) {
        this.roundResultRepository = roundResultRepository;
    }

    public RoundResultModel save(RoundResultEntity round) {
        return toModel(roundResultRepository.save(round));
    }

    public RoundResultModel findById(Long id) {
        return toModel(roundResultRepository.findById(id)
            .orElseThrow(() -> new RoundResultNotFoundException(id)));
    }

    public List<RoundResultModel> findByFilter(Long teamId, Long userId) {
        return toModel(
            roundResultRepository.findAll(createSpecification(teamId, userId)));
    }

    public Specification<RoundResultEntity> createSpecification(Long teamId,
                                                                Long userId) {
        Specification<RoundResultEntity> spec = Specification.where(null);

        if (teamId != null) {
            spec = spec.and(byTeamId(teamId));
        }
        if (userId != null) {
            spec = spec.and(byUserId(userId));
        }

        return spec;
    }

    public void updateById(Long id, RoundResultEntity round) {
        var optionalEntity = roundResultRepository.findById(id);

        if (optionalEntity.isPresent()) {
            var existingEntity = optionalEntity.get();

            existingEntity.setId(round.getId());
            existingEntity.setScore(round.getScore());
            existingEntity.setPoints(round.getPoints());
            existingEntity.setTeam(round.getTeam());
            existingEntity.setRound(round.getRound());

            roundResultRepository.save(existingEntity);
        } else {
            throw new MatchNotFoundException(id);
        }
    }

    public void deleteById(Long id) {
        roundResultRepository.deleteById(id);
    }

    public void deleteByTeamId(Long teamId) {
        roundResultRepository.deleteByTeamId(teamId);
    }

    public void deleteByRoundId(Long roundId) {
        roundResultRepository.deleteByRoundId(roundId);
    }
}
