package com.ss.portal.game.service;

import com.ss.portal.game.repository.GameRepository;
import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.game.exception.GameAlreadyExsitException;
import com.ss.portal.game.exception.GameNotFoundException;
import com.ss.portal.game.model.GameModel;
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
        if (gameRepository.findByName(gameEntity.getName()).isPresent()) {
            throw new GameAlreadyExsitException(
                "Game with name " + gameEntity.getName() + " already exist");
        }
        return GameModel.toModel(gameRepository.save(gameEntity));
    }

    public List<GameModel> findByParams(String name, Long id)
        throws GameNotFoundException {
        if (name == null && id == null) {
            return GameModel.toModel(gameRepository.findAll());
        }

        List<GameModel> gameModels = new ArrayList<>();
        gameModels.add(
            GameModel.toModel(gameRepository.findByParams(name, id).orElseThrow(
                () -> new GameNotFoundException(
                    "Game with name " + name + " and id " + id + " not found"))));

        return gameModels;
    }

    public GameModel updateAllByNameOrId(GameEntity gameEntity, String name,
                                         Long id)
        throws GameNotFoundException, IllegalArgumentException {
        if (name == null && id == null) {
            throw new IllegalArgumentException("Missing required argument");
        }
        gameRepository.updateAllByNameOrId(gameEntity.getName(), name, id);
        return this.findByParams(name, id).get(0);
    }

    public void deleteById(Long id) throws GameNotFoundException {
        if (gameRepository.findById(id).isPresent()) {
            gameRepository.deleteById(id);
        }
        throw new GameNotFoundException("Game with id " + id + " not found");
    }

    public void deleteByName(String name) throws GameNotFoundException {
        if (gameRepository.findByName(name).isPresent()) {
            gameRepository.deleteByName(name);
        }
        throw new GameNotFoundException(
            "Game with name " + name + " not found");
    }
}
