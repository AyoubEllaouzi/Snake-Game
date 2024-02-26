package fr.norsys.snake;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeBoard {
    private static final int COLS = 3;
    private static final int ROWS = 3;
    public List<SnakeCell> snake = new ArrayList<>();
    private SnakeCell snakeCell = new SnakeCell();
    Food food;
    // private Random random = new Random();

    public SnakeBoard() {
        snake.add(new SnakeCell(0, 0));
        snake.add(new SnakeCell(0, 1));
        snakeCell.setDirection(Direction.UP);
    }

    public boolean isOnSnake(int col, int row) {
        for (SnakeCell cell : snake) {
            if (cell.getCol() == col && cell.getRow() == row) {
                return true;
            }
        }
        return false;
    }

    public void move(Direction direction) {
            // Get the current head of the snake.
            SnakeCell currentSnakeHead = snake.get(0);
            int newCol = currentSnakeHead.getCol();
            int newRow = currentSnakeHead.getRow();

            // Determine the new coordinates based on the direction
            switch (direction) {
                case UP:
                    newRow--;
                    break;
                case DOWN:
                    newRow++;
                    break;
                case LEFT:
                    newCol--;
                    break;
                case RIGHT:
                    newCol++;
                    break;
            }

            // Check for collisions with the boundaries and the snake itself
            if (newCol < 0 || newCol >= COLS || newRow < 0 || newRow >= ROWS) {
                throw new IllegalArgumentException("You hit a boundary! Game Over!");
            }
            if (isOnSnake(newCol, newRow)) {
                throw new RuntimeException("You collided with yourself! Game Over!");
            }

           // Move the snake without changing its size
            SnakeCell newSnakeHead = new SnakeCell(newCol, newRow);
            snake.add(0, newSnakeHead);  // add the new head

            // Remove the tail only if the food is not eaten
            if (!isFoodEaten()) {
                snake.remove(snake.size() - 1);
            }

            // update the direction of snake
            snakeCell.setDirection(direction);
            //eat the food
            eatFood();
            //check if win
            checkIfWin();
    }

    public boolean checkIfWin() {
        boolean allCellsCovered = true;

        // Iterate over each cell in the board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                // Check if the cell is not covered by the snake
                if (!isOnSnake(col, row)) {
                    allCellsCovered = false;
                    break;
                }
            }
            // If any cell is not covered, exit the loop
            if (!allCellsCovered) {
                break;
            }
        }

        // Display "You win" if all cells are covered
        if (allCellsCovered) {
            System.out.println("You win!");
        }else {
            System.out.println("find the food!");
        }

        return allCellsCovered;
    }


    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();

        // Draw the board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (isOnSnake(col, row)) { // Swap col and row here
                    if (row == snake.get(0).getRow() && col == snake.get(0).getCol()) { // Swap col and row here
                        boardString.append(" o"); // Snake head
                    } else {
                        boardString.append(" *"); // Snake body
                    }
                } else if (food != null && col == food.getFoodCol() && row == food.getFoodRow()) {
                    boardString.append(" x"); // food
                } else {
                    boardString.append(" ."); // Empty cell
                }
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    public void displaySnakeCell() {
        for (SnakeCell cell : snake) {
            System.out.println(cell.getCol() + " : " + cell.getRow());
        }
    }

    public void generateFood() {
        food = new Food();
        int foodCol = 2,foodRow = 2;
        food.setFoodCol(foodCol);
        food.setFoodRow(foodRow);
    }

    public boolean isFoodEaten() {
        return snake.get(0).getCol() == food.getFoodCol() && snake.get(0).getRow() == food.getFoodRow();
    }

    public void eatFood() {
        if (isFoodEaten()) {
            List<SnakeCell> snakeCopy = new ArrayList<>();
            snakeCopy.add(new SnakeCell(food.getFoodCol(), food.getFoodRow()));
            snakeCopy.addAll(snake);
            snake = snakeCopy;
            food.setFoodCol(4);
            food.setFoodRow(2);
        }
    }
}
