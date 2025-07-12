package Maze;

import Coordinate.Coordinate;
import Game.Game;

import java.util.List;
/**
 * MazeDisplay prints out each cell based on its state. It
 * finds out what it is and prints accordingly, whether it's a cat,
 * it's a wall, etc.
 * The unhideMaze is what reveals a one cell radius of the player.
 */
public class MazeDisplay {
    public boolean isMapRevealed() {
        return mapRevealed;
    }

    public void setMapRevealed(boolean mapRevealed) {
        this.mapRevealed = mapRevealed;
    }
    boolean mapRevealed;

    private void printCell(Game game, Coordinate coordinate){
        Maze maze = game.getMaze();
        MazeCell cell = maze.getCell(coordinate);
        if (cell.isPlayer()) {
            System.out.print("@");
        } else if(cell.isCheese()){
            System.out.print("$");
        }else if (cell.isCat()) {
            System.out.print("!");
        } else if (!cell.isMovable() && (cell.isVisible() || mapRevealed) || cell.isBorder()) {
            System.out.print("#");
        } else if(!cell.isVisible() && !mapRevealed){
            System.out.print(".");
        }else {
            System.out.print(" ");
        }
    }

    private static void unhideMaze(Game game, Coordinate coordinate){
        Maze maze = game.getMaze();
        List<MazeCell> cells = maze.adjacentCells(coordinate);
        cells.add(maze.getCell(coordinate));
        for(MazeCell cell:cells) cell.setVisible(true);
    }

    public void printMaze(Game game){
        unhideMaze(game, game.getPlayerPosition());

        for (int i = 0; i < game.ROWS; i++) {
            for (int j = 0; j < game.COLUMNS; j++) {
                printCell(game, new Coordinate(i,j));
            }
            System.out.println();
        }
    }

    public static void revealMaze(Game game){
        Maze maze = game.getMaze();
        for (int i = 0; i < maze.HEIGHT; i++) {
            for (int j = 0; j < maze.WIDTH; j++) {
                maze.getCell(i,j).setVisible(true);
            }
        }
    }
}
