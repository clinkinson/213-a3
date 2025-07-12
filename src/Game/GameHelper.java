package Game;

import Coordinate.Coordinate;
import Maze.MazeCell;

public class GameHelper {
    /**
     * just checks valid move
     */
    public boolean checkValidMove(MazeCell[][] mazeCells, Coordinate currentCell){
        int column = currentCell.col;
        int row = currentCell.row;
        return mazeCells[row][column].isMovable();
    }
}
