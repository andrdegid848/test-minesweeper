package com.gusev.game.service;

import com.gusev.game.dto.game.GameInfoResponse;
import com.gusev.game.exception.GameAlreadyCompletedException;
import com.gusev.game.exception.GameNotFoundException;
import com.gusev.game.exception.InvalidParametersException;
import com.gusev.game.model.Minesweeper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private static final int MAX_TABLE_SIZE = 30;
    private static final int MIN_TABLE_SIZE = 0;
    private final Map<UUID, Minesweeper> games = new HashMap<>();

    public GameInfoResponse create(int width, int height, int minesCount) {
        if (!isValidParameters(width, height, minesCount)) {
            throw new InvalidParametersException("Invalid parameters");
        }

        UUID gameId = UUID.randomUUID();
        int numberOfOpenCells = width * height - minesCount;
        String[][] fields = new String[height][width];
        Arrays.stream(fields)
                .forEach(row -> Arrays.fill(row, " "));

        Minesweeper game = new Minesweeper(
                gameId,
                width,
                height,
                minesCount,
                fillTable(height, width, minesCount),
                false,
                fields,
                numberOfOpenCells
        );

        games.putIfAbsent(gameId, game);

        return convertGameToResponse(game);
    }

    public GameInfoResponse makeMove(UUID gameId, int row, int column) {
        if (!games.containsKey(gameId)) {
            throw new GameNotFoundException("This game doesn't exist");
        }

        Minesweeper game = games.get(gameId);

        if (game.isCompleted()) {
            throw new GameAlreadyCompletedException("Game is already completed");
        }

        if (!isValidMove(game, row, column)) {
            throw new InvalidParametersException("Incorrect move");
        }

        if (game.getSourceTable()[row][column].equals("X")) {
            game.setCompleted(true);
            game.setFields(game.getSourceTable());
            return convertGameToResponse(game);
        } else if (!game.getFields()[row][column].equals(" ")) {
            throw new InvalidParametersException("This cell is already opened");
        }

        openCell(game, row, column);

        return convertGameToResponse(game);
    }

    private void openCell(Minesweeper game, int row, int column) {
        game.getFields()[row][column] = game.getSourceTable()[row][column];
        game.setNumberOfOpenCells(game.getNumberOfOpenCells() - 1);

        if (game.getNumberOfOpenCells() == 0) {
            game.setCompleted(true);
            changeMineIcon(game);
            return;
        }

        if (game.getSourceTable()[row][column].equals("0")) {
            for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, game.getHeight() - 1); i++) {
                for (int j = Math.max(0, column - 1); j <= Math.min(column + 1, game.getWidth() - 1); j++) {
                    if (!game.getSourceTable()[i][j].equals("X") && game.getFields()[i][j].equals(" ") &&
                        (i != row || j != column)) {
                        openCell(game, i, j);
                    }
                }
            }
        }
    }

    private String[][] fillTable(int height, int width, int minesCount) {
        int placedMines = 0;
        Random random = new Random();
        String[][] fields = new String[height][width];

        while (placedMines < minesCount) {
            int row = random.nextInt(height);
            int column = random.nextInt(width);

            if (fields[row][column] == null) {
                fields[row][column] = "X";
                placedMines++;
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (fields[i][j] == null) {
                    fields[i][j] = String.valueOf(countMines(fields, i, j));
                }
            }
        }

        return fields;
    }

    private int countMines(String[][] fields, int row, int col) {
        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, fields.length - 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, fields[0].length - 1); j++) {
                if (fields[i][j] != null && fields[i][j].equals("X")) {
                    count++;
                }
            }
        }
        return count;
    }

    private void changeMineIcon(Minesweeper game) {
        for (int i = 0; i < game.getFields().length; i++) {
            for (int j = 0; j < game.getFields()[0].length; j++) {
                if (game.getSourceTable()[i][j].equals("X")) {
                    game.getFields()[i][j] = "M";
                }
            }
        }
    }

    private boolean isValidParameters(int width, int height, int minesCount) {
        return width > MIN_TABLE_SIZE && height > MIN_TABLE_SIZE &&
               minesCount > 0 && width <= MAX_TABLE_SIZE && height <= MAX_TABLE_SIZE &&
               minesCount < width * height;
    }

    private boolean isValidMove(Minesweeper game, int row, int column) {
        return row >= 0 && row < game.getHeight() && column >= 0 && column < game.getWidth();
    }

    private GameInfoResponse convertGameToResponse(Minesweeper game) {
        return new GameInfoResponse(
                game.getGameId().toString(),
                game.getWidth(),
                game.getHeight(),
                game.getMinesCount(),
                game.isCompleted(),
                game.getFields()
        );
    }
}