package fr.norsys.snake;


import java.util.ArrayList;
import java.util.List;

public class SnakeBoard {
    private static final int COLS = 3;
    private static final int ROWS = 3;
    public List<SnakeCell> snake = new ArrayList<>();
    private SnakeCell snakeCell = new SnakeCell();
    Food food;

    public SnakeBoard() {
        snake.add(new SnakeCell(0, 0));
        snake.add(new SnakeCell(0, 1));
        snakeCell.setDirection(Direction.UP);
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

            // hit a boundary!
            if (newCol < 0 || newCol >= COLS || newRow < 0 || newRow >= ROWS) {
                throw new IllegalArgumentException("You hit a boundary! Game Over!");
            }
            // eat itself!
            if (isOnSnake(newCol, newRow)) { //check if the current head exist in the snake body
                throw new RuntimeException("You collided with yourself! Game Over!");
            }

            SnakeCell newSnakeHead = new SnakeCell(newCol, newRow);
            // Move the snake without changing its size
            snake.add(0, newSnakeHead);  // add the new head to the snake body

            //if the food is not eaten keep the size of snake
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

    public void generateFood() {
        food = new Food();
        int foodCol = 2,foodRow = 2; //food position
        food.setFoodCol(foodCol);
        food.setFoodRow(foodRow);
    }

    public void eatFood() {
        if (isFoodEaten()) {//check of true(the head position = the food position)
            List<SnakeCell> snakeCopy = new ArrayList<>();//snake copy to add the new head and copy the body of snake
            snakeCopy.add(new SnakeCell(food.getFoodCol(), food.getFoodRow()));
            snakeCopy.addAll(snake);
            snake = snakeCopy;
            food.setFoodCol(4);
            food.setFoodRow(2);
        }
    }
    public boolean isFoodEaten() {// check if the head of snake is in the same position of the food
        return snake.get(0).getCol() == food.getFoodCol() && snake.get(0).getRow() == food.getFoodRow();
    }

    public boolean isOnSnake(int col, int row) {
        for (SnakeCell cell : snake) {
            if (cell.getCol() == col && cell.getRow() == row) {
                return true;
            }
        }
        return false;
    }

    public void displaySnakeCell() {
        for (SnakeCell cell : snake) {
            System.out.println(cell.getCol() + " : " + cell.getRow());
        }
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
}
