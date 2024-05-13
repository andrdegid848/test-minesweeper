package com.gusev.game.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidParametersException extends RuntimeException {

    public InvalidParametersException(String message) {
        super(message);
    }
}
