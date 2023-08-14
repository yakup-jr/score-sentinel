package com.ss.portal.tournament.service;

import com.ss.portal.tournament.entity.TournamentResultEntity;
import com.ss.portal.tournament.exception.TournamentResultNotFoundException;
import com.ss.portal.tournament.model.TournamentResultModel;
import com.ss.portal.tournament.repository.TournamentResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ss.portal.shared.repository.SharedRepository.Specs.byTeamId;
import static com.ss.portal.tournament.model.TournamentResultModel.toModel;
import static com.ss.portal.tournament.repository.TournamentResultRepository.Specs.byTournamentId;

@Service
public class TournamentResultService {

    private final TournamentResultRepository tournamentResultRepository;

    @Autowired
    public TournamentResultService(
        TournamentResultRepository tournamentResultRepository) {
        this.tournamentResultRepository = tournamentResultRepository;
    }

    public TournamentResultModel save(TournamentResultEntity tournamentResult) {
        var newTournamentResult = tournamentResultRepository.save(tournamentResult);

        return toModel(newTournamentResult);
    }

    public TournamentResultModel findById(Long id) {
        var tournamentResult = tournamentResultRepository.findById(id);

        return toModel(tournamentResult.orElseThrow(
            () -> new TournamentResultNotFoundException(id)));
    }

    public List<TournamentResultModel> findByFilter(Long teamId, Long tournamentId) {
        return toModel(tournamentResultRepository.findAll(
            createSpecification(teamId, tournamentId)));
    }

    public Specification<TournamentResultEntity> createSpecification(Long teamId,
                                                                     Long tournamentId) {
        Specification<TournamentResultEntity> spec = Specification.where(null);

        if (teamId != null) spec = spec.and(byTeamId(teamId));
        if (tournamentId != null) spec = spec.and(byTournamentId(tournamentId));

        return spec;
    }

    public void updateById(Long id, TournamentResultEntity tournamentResult) {
        var optionalEntity = tournamentResultRepository.findById(id);

        if (optionalEntity.isPresent()) {
            var existingEntity = optionalEntity.get();

            existingEntity.setId(tournamentResult.getId());
            existingEntity.setScore(tournamentResult.getScore());
            existingEntity.setPoints(tournamentResult.getPoints());
            existingEntity.setTeam(tournamentResult.getTeam());
            existingEntity.setTournament(tournamentResult.getTournament());

            tournamentResultRepository.save(existingEntity);
        } else {
            throw new TournamentResultNotFoundException(id);
        }
    }

    public void deleteById(Long id) {
        tournamentResultRepository.deleteById(id);
    }

    public void deleteByTeamId(Long teamId) {
        tournamentResultRepository.deleteByTeamId(teamId);
    }

    public void deleteByTournamentId(Long tournamentId) {
        tournamentResultRepository.deleteByTournamentId(tournamentId);
    }

}
