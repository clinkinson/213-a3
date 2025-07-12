package Maze;
import Coordinate.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//maze generation algorithm referenced from https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_depth-first_search
public class Maze {
    /**
     * Maze is the class that generates the maze and manages things
     * like the cell at a "coordinate", which just returns the cell at the row & column
     * The maze board is really just a 2d array of MazeCells
     */
    public  final double CYCLE_PERCENTAGE = 0.1;
    public  final int HEIGHT = 15;
    public  final int WIDTH = 20;
    private final int columns;
    private final int rows;
    private final MazeCell[][] maze;
    final Random random;

    public Maze(int rows, int columns) {
        this.random = new Random(System.nanoTime());
        this.columns = columns;
        this.rows = rows;
        this.maze = new MazeCell[rows][columns];

        initializeMaze();

    }

    private void initializeMaze() {

        while(true){
            initializeCell();
            populateWalls();
            generateMaze(new Coordinate(1,1));

            if(validateNo2x2Cells() && validatePlayersPosition()){
                induceCycles();
                break;
            }
        }
    }

    private void initializeCell() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                this.maze[x][y] = new MazeCell();
            }
        }
    }

    public MazeCell[][] getMaze(){
        return maze;
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public MazeCell getCell(int x, int y){
        return maze[x][y];
    }

    public MazeCell getCell(Coordinate coordinate){
        return maze[coordinate.row][coordinate.col];
    }

    public boolean isInside(int x,int y){
        return isInside(new Coordinate(x,y));
    }
    public boolean isInside(Coordinate coordinate) {
        int x = coordinate.row;
        int y = coordinate.col;
        return x > 0 && y > 0 && x < rows - 1 && y < columns - 1;
    }

    private void generateMaze(Coordinate coordinate){
        List<MovableDirection> directions = new ArrayList<>(List.of(MovableDirection.values()));
        Collections.shuffle(directions);

        for(MovableDirection direction:directions){
            int dx = coordinate.row + direction.dx;
            int dy = coordinate.col + direction.dy;
            Coordinate newCoordinate = new Coordinate(dx,dy);
            Coordinate wallCoordinate = getWallBetweenCells(coordinate, newCoordinate);
            if (!isInside(new Coordinate(dx,dy)) || !isInside(wallCoordinate)) continue;
            if(getCell(wallCoordinate).isMovable() && !getCell(wallCoordinate).isAlgorithmVisited()) newCoordinate = wallCoordinate;
            if (maze[dx][dy].isAlgorithmVisited()) continue;

            getCell(coordinate).setAlgorithmVisited(true);
            getCell(newCoordinate).setAlgorithmVisited(true);

            getCell(wallCoordinate).setAlgorithmVisited(true);
            getCell(wallCoordinate).setMovable(true);
            generateMaze(newCoordinate);
        }
    }

    private Coordinate getWallBetweenCells(Coordinate a, Coordinate b){
        int diffX = a.row - b.row;
        int diffY = a.col - b.col;
        Coordinate coordinate = new Coordinate(0,0);

        if(diffX == 0){
            coordinate.row = a.row;
        }else{
            coordinate.row = Integer.min(a.row, b.row) + 1;
        }
        if(diffY == 0){
            coordinate.col = a.col;
        }else{
            coordinate.col = Integer.min(a.col, b.col) + 1;
        }
        return coordinate;
    }



    private boolean validateNo2x2Cells(){
        for(int x = 1; x < rows; x++){
            for(int y = 1; y < columns; y++){
                List<MazeCell> cells = getFourCells(new Coordinate(x,y));
                if(cells.size() < 4){
                    continue;
                }
                if(invalidFourCells(cells)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean invalidFourCells(List<MazeCell> cells){
        int numEmpty = 0;
        int numWall = 0;
        for(MazeCell cell : cells){
            if(cell.isMovable()) numEmpty += 1;
            else numWall += 1;
        }
        return numEmpty >= 4 || numWall >= 4;
    }

    private List<MazeCell> getFourCells(Coordinate coordinate){
        int x = coordinate.row;
        int y = coordinate.col;
        List<MazeCell> cells = new ArrayList<>(4);
        for(int i = x; i < x + 2; i++){
            for(int j = y; j < y + 2; j++){
                if(!isInside(new Coordinate(i,j))) continue;
                cells.add(maze[i][j]);
            }
        }
        return cells;

    }

    private boolean validatePlayersPosition(){
        Coordinate playerCoordinate = new Coordinate(1,1);
        Coordinate cat1Coordinate = new Coordinate(rows - 2, columns - 2);
        Coordinate cat2Coordinate = new Coordinate(1, columns - 2);
        Coordinate cat3Coordinate = new Coordinate(rows - 2, 1);

        List<Coordinate> coordinates = new ArrayList<>();
        Collections.addAll(coordinates,playerCoordinate,cat1Coordinate,cat2Coordinate,cat3Coordinate);

        for(Coordinate coordinate:coordinates){
            List<MazeCell> cells = adjacentCells(coordinate);
            int openCells = 0;
            for(MazeCell cell:cells){
                if(cell.isMovable()) openCells += 1;
            }
            if(openCells == 0) return false;
        }


        return true;
    }

    public List<MazeCell> adjacentCells(Coordinate coordinate){
        List<MazeCell> cells = new ArrayList<>();
        for (int i = coordinate.row - 1 ; i <= coordinate.row + 1; i++){
            for(int j = coordinate.col - 1; j <= coordinate.col + 1; j++){
                if(isInside(i,j) && !getCell(i,j).isBorder() && !(new Coordinate(i, j)).equals(coordinate)){
                    cells.add(getCell(i,j));
                }
            }
        }
        return cells;
    }

    private List<Coordinate> getAllWalls(){
        List<Coordinate> coordinates = new ArrayList<>();
        for(int i = 1; i < rows-1; i++){
            for(int j = 1 ; j < columns-1; j++){
                if(!getCell(i,j).isMovable()) {
                    coordinates.add(new Coordinate(i, j));
                }
            }
        }
        return coordinates;
    }

    private void induceCycles(){
            int totalLoop = 0;
//            int limit = random.nextInt(1, (int) ((rows-1)*(columns-1)* CYCLE_PERCENTAGE));
            int limit = (int) ((rows-1)*(columns-1)* CYCLE_PERCENTAGE);
            List<Coordinate> coordinates = getAllWalls();
            while (totalLoop <= limit && !coordinates.isEmpty()){
                Collections.shuffle(coordinates);
                boolean flipCoin = random.nextBoolean();
                if(!flipCoin){
                    continue;
                }

                Coordinate coordinate = coordinates.remove(0);
                if(!isInside(coordinate) || getCell(coordinate).isMovable()){
                    continue;
                }

                getCell(coordinate).setMovable(true);
                if(!validateNo2x2Cells()){
                    getCell(coordinate).setMovable(false);
                }else{
                    totalLoop += 1;
                }



        }
    }
    void populateWalls(){
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < columns; y++){
                //generate top-bottom wall
                if(x == 0){
                    maze[0][y].setBorder();
                    maze[rows - 1][y].setBorder();
                }
                //generate outermost left-right wall
                if(y == 0){
                    maze[x][0].setBorder();
                    maze[x][columns - 1].setBorder();
                }
                //generate grid
                if(x % 2 == 0){
                    this.maze[x][y].setMovable(false);
                }
                if((y) % 2 == 0 && y != columns -2){
                    this.maze[x][y].setMovable(false);
                }
            }
        }
    }

    private enum MovableDirection {
        NORTH(-2, 0),
        EAST(0, 2),
        SOUTH(2, 0),
        WEST(0, -2);

        private final int dx;
        private final int dy;

        MovableDirection(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }


    public static void main(String[] args) {
        Maze maze = new Maze(15,20);
        maze.display();
    }
}
