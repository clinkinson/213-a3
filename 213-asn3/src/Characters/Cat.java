package Characters;

import Coordinate.Coordinate;
import Maze.Maze;

public class Cat implements Character {
    /**
     * Cat implements the Character interface.
     * However, it does have one difference:
     * the autoMaze() method which basically
     * just has the cat chose a random direction and move there
     */
    public Cat(Coordinate coordinate){
        this.coordinate = coordinate;
    }
    private Coordinate coordinate;
    @Override
    public void moveCharacter(int row, int column) {
        coordinate = new Coordinate(row,column);
    }
    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean inSamePosition(Character character) {
        return coordinate.equals(character.getCoordinate());
    }


    public Coordinate autoMove(Maze maze) {
        Coordinate randomCoordinate;
        while(true){
            randomCoordinate = Coordinate.getRandomCoordinate(
                    coordinate.row - 1, coordinate.row + 1,
                    coordinate.col - 1, coordinate.col + 1
            );
            if(!coordinate.equals(randomCoordinate)
                    && maze.isInside(randomCoordinate)
                    && maze.getCell(randomCoordinate).isMovable()){
                break;
            }
        }
        coordinate = randomCoordinate;

        return randomCoordinate;
    }
}
