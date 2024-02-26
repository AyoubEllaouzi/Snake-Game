package fr.norsys.snake;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {

    @Test
    public void testGameMoveRight() {
        SnakeBoard snakeBoard = new SnakeBoard();
        snakeBoard.generateFood();

        int expectedCol = 1;
        int expectedRow = 0;
        System.out.println("Initial State:");
        System.out.println(snakeBoard);

        System.out.println("After moving right:");
        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard);

        assertEquals(expectedCol, snakeBoard.snake.get(0).getCol());
        assertEquals(expectedRow, snakeBoard.snake.get(0).getRow());
    }

    @Test
    public void testGameMoveDown() {
        SnakeBoard snakeBoard = new SnakeBoard();
        snakeBoard.generateFood();

        int expectedCol = 1;
        int expectedRow = 1;
        System.out.println("Initial State:");
        System.out.println(snakeBoard);

        snakeBoard.move(Direction.RIGHT);
        System.out.println("After moving Down:");
        snakeBoard.move(Direction.DOWN);
        System.out.println(snakeBoard);

        assertEquals(expectedCol, snakeBoard.snake.get(0).getCol());
        assertEquals(expectedRow, snakeBoard.snake.get(0).getRow());
    }

    @Test
    public void testGameMoveLeft() {
        SnakeBoard snakeBoard = new SnakeBoard();
        snakeBoard.generateFood();

        int expectedCol = 0;
        int expectedRow = 1;
        System.out.println("Initial State:");
        System.out.println(snakeBoard);

        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard);
        snakeBoard.move(Direction.DOWN);
        System.out.println(snakeBoard);
        System.out.println("After moving Left:");
        snakeBoard.move(Direction.LEFT);
        System.out.println(snakeBoard);

        assertEquals(expectedCol, snakeBoard.snake.get(0).getCol());
        assertEquals(expectedRow, snakeBoard.snake.get(0).getRow());
    }

    @Test
    public void testGameMoveUp() {
        SnakeBoard snakeBoard = new SnakeBoard();
        snakeBoard.generateFood();

        int expectedCol = 0;
        int expectedRow = 0;
        System.out.println("Initial State:");
        System.out.println(snakeBoard);

        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard);
        snakeBoard.move(Direction.DOWN);
        System.out.println(snakeBoard);
        snakeBoard.move(Direction.LEFT);
        System.out.println("After moving Up:");
        snakeBoard.move(Direction.UP);
        System.out.println(snakeBoard);

        assertEquals(expectedCol, snakeBoard.snake.get(0).getCol());
        assertEquals(expectedRow, snakeBoard.snake.get(0).getRow());
    }


    @Test
    public void testHitBoundary() {
        SnakeBoard snakeBoard = new SnakeBoard();
        snakeBoard.generateFood();

        System.out.println("Initial State:");
        System.out.println(snakeBoard);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            snakeBoard.displaySnakeCell();
            snakeBoard.move(Direction.RIGHT);
            snakeBoard.move(Direction.RIGHT);
            snakeBoard.move(Direction.RIGHT);
            System.out.println(snakeBoard);
        });

        // Verify the exception message
        String expectedMessage = "You hit a boundary! Game Over!";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));

    }
    @Test
    public void testGameFromRightToLeft() {
        SnakeBoard snakeBoard = new SnakeBoard();
        snakeBoard.generateFood();
        System.out.println(snakeBoard);

        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            System.out.println("After moving right again:");
            snakeBoard.move(Direction.LEFT);
            System.out.println(snakeBoard);
        });

        // Verify the exception message
        String expectedMessage = "You collided with yourself! Game Over!";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testGameGenerateFood() {
        SnakeBoard snakeBoard = new SnakeBoard();
        // Record initial food position
        int expectedCol = 2;
        int expectedRow = 2 ;

        System.out.println("Initial State:");
        System.out.println(snakeBoard);
        // Generate food
        snakeBoard.generateFood();
        System.out.println("After Generate food:");
        System.out.println(snakeBoard);
        assertEquals(expectedCol, snakeBoard.food.getFoodCol());
        assertEquals(expectedRow, snakeBoard.food.getFoodRow());

    }

    @Test
    public void testGameEatFood() {
        SnakeBoard snakeBoard = new SnakeBoard();
        int expectedSnakeSize = 3;
        System.out.println("Initial State:");
        System.out.println(snakeBoard);
        snakeBoard.generateFood();

        System.out.println("After moving right:");
        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard);


        System.out.println("After moving right again:");
        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard);

        System.out.println("After moving down:");
        snakeBoard.move(Direction.DOWN);
        System.out.println(snakeBoard);


        System.out.println("After moving down again:");
        snakeBoard.move(Direction.DOWN);
        System.out.println(snakeBoard);

        System.out.println("After moving left:");
        snakeBoard.move(Direction.LEFT);
        System.out.println(snakeBoard);

        assertEquals(expectedSnakeSize, snakeBoard.snake.size()-1); // -1 for the cell of food
    }


    @Test
    public void testGameEatSelf() {
        SnakeBoard snakeBoard = new SnakeBoard();
        snakeBoard.generateFood();

        System.out.println("Initial State:");
        System.out.println(snakeBoard);

        System.out.println("After moving right:");
        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard);


        System.out.println("After moving right again:");
        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard);

        System.out.println("After moving down:");
        snakeBoard.move(Direction.DOWN);
        System.out.println(snakeBoard);


        System.out.println("After moving down again:");
        snakeBoard.move(Direction.DOWN);
        System.out.println(snakeBoard);

        //change Food Location
        snakeBoard.food.setFoodCol(1);
        snakeBoard.food.setFoodRow(2);
        System.out.println(snakeBoard);

        System.out.println("After moving Left:");
        snakeBoard.move(Direction.LEFT);
        System.out.println(snakeBoard);

        //change Food Location again
        snakeBoard.food.setFoodCol(1);
        snakeBoard.food.setFoodRow(0);
        System.out.println(snakeBoard);

        System.out.println("After moving Up:");
        snakeBoard.move(Direction.UP);
        System.out.println(snakeBoard);


        System.out.println("Eat Self :(");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            snakeBoard.move(Direction.RIGHT);
            snakeBoard.eatFood();
        });

        // Check if the exception message matches the expected message
        String expectedMessage = "You collided with yourself! Game Over!";
        String actualMessage = exception.getMessage();
        assert(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGameWinGame() {
        SnakeBoard snakeBoard = new SnakeBoard();
        boolean expected = true;
        snakeBoard.snake.add(new SnakeCell(0, 2));
        snakeBoard.snake.add(new SnakeCell(1, 2));
        snakeBoard.snake.add(new SnakeCell(2, 2));
        snakeBoard.snake.add(new SnakeCell(2, 1));
        snakeBoard.snake.add(new SnakeCell(2, 0));

        System.out.println("Initial State:");
        System.out.println(snakeBoard.toString());
        snakeBoard.generateFood();

        snakeBoard.food.setFoodCol(1);
        snakeBoard.food.setFoodRow(0);
        System.out.println(snakeBoard.toString());

        System.out.println("After moving right:");
        snakeBoard.move(Direction.RIGHT);
        System.out.println(snakeBoard.toString());

        snakeBoard.food.setFoodCol(1);
        snakeBoard.food.setFoodRow(1);

        System.out.println("After moving right Down:");
        snakeBoard.move(Direction.DOWN);

        boolean result  = snakeBoard.checkIfWin();
        assertEquals(expected, result);

    }

}
