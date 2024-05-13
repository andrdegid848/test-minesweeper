package com.gusev.game.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GameAlreadyCompletedException extends RuntimeException {

    public GameAlreadyCompletedException(String message) {
        super(message);
    }
}
