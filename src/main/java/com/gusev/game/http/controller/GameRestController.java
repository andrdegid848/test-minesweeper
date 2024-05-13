package com.gusev.game.http.controller;

import com.gusev.game.dto.game.GameInfoResponse;
import com.gusev.game.dto.game.GameTurnRequest;
import com.gusev.game.dto.game.NewGameRequest;
import com.gusev.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game")
public class GameRestController {

    private final GameService gameService;

    @PostMapping("/new")
    public ResponseEntity<Object> create(@RequestBody NewGameRequest newGameRequest) {
        GameInfoResponse game = gameService.create(newGameRequest.width(), newGameRequest.height(), newGameRequest.minesCount());
        return ResponseEntity.ok(game);
    }

    @PostMapping("/turn")
    public ResponseEntity<Object> makeMove(@RequestBody GameTurnRequest gameTurnRequest) {
        GameInfoResponse game = gameService.makeMove(UUID.fromString(gameTurnRequest.gameId()), gameTurnRequest.row(), gameTurnRequest.column());
        return ResponseEntity.ok(game);
    }
}


