package com.gusev.game.dto.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GameInfoResponse(
        String gameId,
        int width,
        int height,
        int minesCount,
        @JsonProperty("completed") boolean isCompleted,
        @JsonProperty("field") String[][] fields
) {
}

