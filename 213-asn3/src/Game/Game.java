package Game;
import Characters.Cat;
import Characters.Player;
import Coordinate.Coordinate;
import Coordinate.Direction;
import Maze.Maze;

import java.util.ArrayList;
import java.util.List;

public class Game {
    /**
     * MazeGrid manages the game, from generating the board, to checking if
     * the player has won or not, managing the movement of the player.
     */
    Maze maze;
    GameHelper mazeGridHelper = new GameHelper();
    public final int ROWS = 15;
    public final int COLUMNS = 20;
    Player player;
    List<Cat> cats = new ArrayList<>(3);
    Coordinate cheeseCoordinate;
    boolean isLost;
    boolean isWon;
    public int cheeseToWin = 5;
    public int cheeseObtained = 0;
    public Game() {
        initializeGame();
    }

    public void initializeGame() {
        maze = new Maze( ROWS, COLUMNS);
        setInitialCoordinate();
        this.isLost = false;
        this.isWon = false;
    }

    public Maze getMaze(){
        return maze;
    }

    public Coordinate getPlayerPosition(){
        return player.getCoordinate();
    }


    public void handlePlayerMove(Direction direction){
        if(isWon || isLost) initializeGame();
        Coordinate playerCoordinate = player.getCoordinate();
        Coordinate offsetCoordinate = new Coordinate(0,0);
        switch (direction) {
            case UP -> offsetCoordinate = playerCoordinate.getOffset(-1, 0);
            case LEFT -> offsetCoordinate = playerCoordinate.getOffset(0, -1);
            case DOWN -> offsetCoordinate = playerCoordinate.getOffset(1, 0);
            case RIGHT -> offsetCoordinate = playerCoordinate.getOffset(0, 1);
        }
        updatePlayerPosition(offsetCoordinate);
        updateCatsPosition();
        checkWinCondition();
        checkLostCondition();
    }

    public void updatePlayerPosition(Coordinate coordinate){
        if(!mazeGridHelper.checkValidMove(maze.getMaze(), coordinate)) {
            System.out.println("INVALID Move");
            return;
        }
        maze.getCell(player.getCoordinate()).setPlayer(false);
        maze.getCell(player.getCoordinate()).setPlayerVisited(true);
        player.moveCharacter(coordinate);
        maze.getCell(player.getCoordinate()).setPlayer(true);
    }

    public void updateCatsPosition(){
        for(Cat cat:cats){
            Coordinate oldCoordinate = cat.getCoordinate();
            Coordinate newCoordinate = cat.autoMove(maze);
            maze.getCell(oldCoordinate).setCat(false);
            maze.getCell(newCoordinate).setCat(true);
        }
    }
    public void checkLostCondition(){
        for(Cat cat:cats){
            if (cat.inSamePosition(player)){
                isLost = true;
                return;
            }
        }
        isLost = false;

    }
    public boolean getIsLost(){ return isLost; }
    public void checkWinCondition(){
        if(player.getCoordinate().equals(cheeseCoordinate)) {
            isWon = true;
            cheeseObtained += 1;
        };
    }
    public boolean getWin(){
        return isWon;
    }

    public void setCheat(){
        cheeseToWin = 1;
    }

    private void setInitialCoordinate() {
        Coordinate playerCoordinate = new Coordinate(1,1);
        this.player = new Player(playerCoordinate);
        maze.getCell(playerCoordinate).setPlayer(true);
        initializeCatCoordinate();
        initializeCheeseCoordinate();
    }

    private void initializeCheeseCoordinate() {
        Coordinate playerCoordinate = player.getCoordinate();

        //        keeps generating random coordinate if the in same position as other players
        while (true){
            Coordinate randomCoordinate = Coordinate.getRandomCoordinate(1, ROWS - 2, 1, COLUMNS - 2);
            if(!maze.isInside(randomCoordinate) || !maze.getCell(randomCoordinate).isMovable()) continue;
//            check if cheese is spawned in same place as other players
            List<Coordinate> catCoordinates = cats.stream().map(Cat::getCoordinate).toList();

            if(catCoordinates.contains(randomCoordinate)) continue;
            if(playerCoordinate.equals(randomCoordinate)) continue;

            this.cheeseCoordinate = randomCoordinate;
            maze.getCell(cheeseCoordinate).setCheese(true);
            break;
        }
    }

    private void initializeCatCoordinate() {
        Coordinate cat1Coordinate = new Coordinate(ROWS - 2, COLUMNS - 2);
        maze.getCell(cat1Coordinate).setCat(true);
        Coordinate cat2Coordinate = new Coordinate(1, COLUMNS - 2);
        maze.getCell(cat2Coordinate).setCat(true);
        Coordinate cat3Coordinate = new Coordinate(ROWS - 2, 1);
        maze.getCell(cat3Coordinate).setCat(true);
        cats.add(new Cat(cat1Coordinate));
        cats.add(new Cat(cat2Coordinate));
        cats.add(new Cat(cat3Coordinate));
    }
}
