package com.ss.portal.game.service;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.game.exception.GameAlreadyExsitException;
import com.ss.portal.game.exception.GameNotFoundException;
import com.ss.portal.game.model.GameModel;
import com.ss.portal.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameModel addOne(GameEntity gameEntity)
        throws GameAlreadyExsitException {
        if (gameRepository.findByParams(gameEntity.getName(), null)
            .isPresent()) {
            throw new GameAlreadyExsitException(
                "Game with name " + gameEntity.getName() + " already exist");
        }
        return GameModel.toModel(gameRepository.save(gameEntity));
    }

    public List<GameModel> findAll() {
        return GameModel.toModel(gameRepository.findAll());
    }

    public List<GameModel> findByParams(String name, Long id)
        throws GameNotFoundException {
        List<GameModel> gameModels = new ArrayList<>();
        gameModels.add(
            GameModel.toModel(gameRepository.findByParams(name, id).orElseThrow(
                () -> new GameNotFoundException(name, id))));

        return gameModels;
    }

    public GameModel updateAllById(GameEntity gameEntity,
                                   Long id)
        throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Missing required argument");
        }
        gameRepository.updateAllById(gameEntity.getName(), id);
        return this.findByParams(null, id).get(0);
    }

    public void deleteById(Long id) throws GameNotFoundException {
        if (gameRepository.findById(id).isPresent()) {
            gameRepository.deleteById(id);
        }
        throw new GameNotFoundException("Game with id " + id + " not found");
    }

    public void deleteByName(String name) throws GameNotFoundException {
        if (gameRepository.findByParams(name, null).isPresent()) {
            gameRepository.deleteByName(name);
        }
        throw new GameNotFoundException(
            "Game with name " + name + " not found");
    }
}
