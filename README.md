# test-minesweeper

## Specification

Each game begins by specifying the field size, width and height, as well as the number of mines_count on it. The original problem does not imply restrictions, but for the test implementation we will focus on a reasonable limitation of the input parameters: width and height no more than 30, number of mines no more than width * height - 1 (there should always be one free cell).

Next, the player enters the created game (game identification by the game_id received in the response) makes moves, indicating the coordinates of the cell the player wants to open, namely row (row numbers, starting from zero) and col (column numbers, starting from zero).

In response to both methods, data about the game itself is received: the unique game identifier game_id, the size of the field and the number of mines received when creating the game, as well as data about the field field in the form of two-dimensional array characters (a string of height, in each element in width), where empty lines " " (spaces) mean unopened cell fields, fields with numbers "0" to "8" mean open cells, where the numbers indicate how many mines are located in a sequential direction from the current cell. The parameter is also returned: completed, specified, current game completed.

The game ends if the user has indicated a cell where a mine is installed (cells with mines are marked with the symbol "X" - Latin capital "X"), or the user has opened all cells not occupied by mines (in this case, the mines are marked "M" ") "-Latin capital "um") Also, upon completion of the game, information on all quarantine cells should appear - the number of mines next to each cell.

If during the game the user opens a cell that has no mines next to it (that is, a cell with a value of "0"), he must "open" all the corresponding cells that have no mines next to them, as well as all the corresponding ones. Numerical cells located next to world mines determine their number.

Do not allow new moves to be made after the game is completed, and also re-check an already checked cell. These and other error situations should return a 400 Bad Request error with a text description of the error
