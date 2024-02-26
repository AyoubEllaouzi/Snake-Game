package fr.norsys.snake;

public class SnakeCell {
    private int col;
    private int row;
    Direction oldDirection;

    public SnakeCell() {

    }
    public SnakeCell(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public void setDirection(Direction direction){
        this.oldDirection = direction;
    }
    public int getCol(){return this.col;}
    public int getRow(){return this.row;}

}
