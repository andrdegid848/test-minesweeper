package com.gusev.game.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String message) {
        super(message);
    }
}
