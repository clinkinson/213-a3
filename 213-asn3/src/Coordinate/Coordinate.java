package Coordinate;

import java.util.Random;


/**
 * <h3>Coordinate</h3>
 * Coordinate manages position of each cells and provides methods to support cell management for the Maze
 */
public class Coordinate {
    public int row;
    public int col;
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Coordinate)) return false;

        Coordinate coordinate = (Coordinate) obj;
        return row == coordinate.row && col == coordinate.col;
    }

    /**
     * Returns off-setted coordinate. Does not mutate the calling object.
     * @param offsetRow An offset value of row.
     * @param offsetCol An offset value of column.
     * @return an off-setted copy of the calling object.
     */
    public Coordinate getOffset(int offsetRow, int offsetCol){
        return new Coordinate(row + offsetRow, col + offsetCol);
    }

    public static Coordinate getRandomCoordinate(int minRow, int maxRow, int minCol, int maxCol){
        //bounds of random value: [currentValue, rowMax/colMax]
        int newRows = new Random().nextInt(minRow, maxRow + 1);
        int newColumns = new Random().nextInt(minCol, maxCol + 1);
        return new Coordinate(newRows,newColumns);
    }

    @Override
    public String toString() {
        return "[" + row + "," + col + "]";
    }
}
