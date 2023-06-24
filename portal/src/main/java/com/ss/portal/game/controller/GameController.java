package com.ss.portal.game.controller;

import com.ss.portal.game.entity.GameEntity;
import com.ss.portal.game.model.GameModel;
import com.ss.portal.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game-manager")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games")
    public ResponseEntity<?> addOne(@RequestBody GameEntity gameEntity) {
        try {
            GameModel newGameModel = gameService.addOne(gameEntity);
            return ResponseEntity.status(201).body(newGameModel);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @GetMapping("/games")
    public ResponseEntity<?> findByParams(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "id", required = false) Long id) {
        try {
            List<GameModel> gameModels = gameService.findByParams(name, id);
            return ResponseEntity.status(200).body(gameModels);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @PutMapping("games")
    public ResponseEntity<?> updateAllByNameOrId(
        @RequestBody GameEntity gameEntity,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "id", required = false) Long id) {
        try {
            GameModel gameModel = gameService.updateAllByNameOrId(gameEntity,
                name, id);
            return ResponseEntity.status(200).body(gameModel);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @DeleteMapping("/games")
    public ResponseEntity<?> deleteById(@RequestParam("id") Long id) {
        try {
            gameService.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @DeleteMapping("/games/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name) {
        try {
            gameService.deleteByName(name);
            return ResponseEntity.status(204).build();
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
