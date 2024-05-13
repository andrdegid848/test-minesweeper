package com.gusev.game.http.handler;

import com.gusev.game.exception.GameAlreadyCompletedException;
import com.gusev.game.exception.GameNotFoundException;
import com.gusev.game.exception.InvalidParametersException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice(basePackages = "com.gusev.game.http.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GameAlreadyCompletedException.class, GameNotFoundException.class, InvalidParametersException.class})
    public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
