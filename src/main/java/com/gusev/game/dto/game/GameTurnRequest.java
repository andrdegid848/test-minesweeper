package com.gusev.game.dto.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GameTurnRequest(
        String gameId,
        @JsonProperty("col") int column,
        int row
) {
}

