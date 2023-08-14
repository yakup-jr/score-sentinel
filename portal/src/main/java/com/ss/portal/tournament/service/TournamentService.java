package com.ss.portal.tournament.service;

import com.ss.portal.tournament.entity.TournamentEntity;
import com.ss.portal.tournament.exception.TournamentNofFoundException;
import com.ss.portal.tournament.model.TournamentModel;
import com.ss.portal.tournament.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ss.portal.shared.repository.SharedRepository.Specs.byGameId;
import static com.ss.portal.shared.repository.SharedRepository.Specs.byTeamId;
import static com.ss.portal.tournament.model.TournamentModel.toModel;
import static com.ss.portal.tournament.repository.TournamentRepository.Specs.byRoundId;
import static com.ss.portal.tournament.repository.TournamentRepository.Specs.byTournamentResult;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public TournamentModel save(TournamentEntity tournament) {
        var newTournament = tournamentRepository.save(tournament);

        return toModel(newTournament);
    }

    public TournamentModel findById(Long id) {
        var tournament = tournamentRepository.findById(id);

        return toModel(tournament.orElseThrow(() -> new TournamentNofFoundException(id)));
    }

    public List<TournamentModel> findByFilter(Long teamId, Long roundId, Long gameId,
                                              Long tournamentResultId) {
        return toModel(tournamentRepository.findAll(
            createSpecification(teamId, roundId, gameId, tournamentResultId)));
    }

    public Specification<TournamentEntity> createSpecification(Long teamId,
                                                               Long roundId,
                                                               Long gameId,
                                                               Long tournamentResultId) {

        var spec = Specification.<TournamentEntity>where(null);

        if (teamId != null) spec.and(byTeamId(teamId));
        if (roundId != null) spec.and(byRoundId(roundId));
        if (gameId != null) spec.and(byGameId(gameId));
        if (tournamentResultId != null) spec.and(byTournamentResult(tournamentResultId));

        return spec;
    }

    public void updateById(Long id, TournamentEntity tournament) {
        var optionalEntity = tournamentRepository.findById(id);

        if (optionalEntity.isPresent()) {
            var existingEntity = new TournamentEntity();

            existingEntity.setId(tournament.getId());
            existingEntity.setName(tournament.getName());
            existingEntity.setTeams(tournament.getTeams());
            existingEntity.setRounds(tournament.getRounds());
            existingEntity.setGames(tournament.getGames());
            existingEntity.setTournamentResults(tournament.getTournamentResults());

            tournamentRepository.save(existingEntity);
        } else {
            new TournamentNofFoundException(id);
        }
    }

    public void deleteById(Long id) {
        tournamentRepository.deleteById(id);
    }

    public void deleteByTeamId(Long teamId) {
        tournamentRepository.deleteByTeamId(teamId);
    }

    public void deleteByGameId(Long gameId) {
        tournamentRepository.deleteByGameId(gameId);
    }

}
