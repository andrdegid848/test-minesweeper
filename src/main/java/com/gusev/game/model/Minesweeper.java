package com.gusev.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Minesweeper {
    private final UUID gameId;
    private final int width;
    private final int height;
    private final int minesCount;
    private final String[][] sourceTable;
    private boolean isCompleted;
    private String[][] fields;
    private int numberOfOpenCells;
}
