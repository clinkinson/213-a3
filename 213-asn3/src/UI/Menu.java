package UI;
import Maze.MazeDisplay;
import Game.Game;
import static Coordinate.Direction.*;

public class Menu {
    /**
     * Handles player moves and other options
     */

    static Game game = new Game();
    static MazeDisplay mazeDisplay = new MazeDisplay();
    static final String ErrorOption = """
            Invalid Option. Please choose W (Up), A (Left), S (Down), D (Right), or ? (Display help).
            """;
    public void invokeMenu(){
        MenuHelper.printWelcomeMessage();
        MenuHelper.printHelp();
        do {
            updateCheeseCollected(game);
            mazeDisplay.printMaze(game);
            String option = MenuHelper.getInput("[WASDwasd?MmCc]", ErrorOption);
            if (option.equalsIgnoreCase("q")) break;
            handleOptions(option);

        } while (!checkWinLost(game));
    }

    private static boolean checkWinLost(Game game) {

        if(game.cheeseToWin == game.cheeseObtained){
            System.out.println("Congratulations! You've got all the cheese!");
            MazeDisplay.revealMaze(game);
            mazeDisplay.printMaze(game);
            return true;
        }
        if (game.getIsLost()) {
            System.out.println("YOU LOST! Better luck next time :(");
            MazeDisplay.revealMaze(game);
            mazeDisplay.printMaze(game);
            return true;
        }
        if(game.getWin() && !game.getIsLost()){
            MazeDisplay.revealMaze(game);
            mazeDisplay.printMaze(game);
            game.initializeGame();
            System.out.println("CONGRATULATIONS! You won!");
        }

        return false;
    }
    public static void updateCheeseCollected(Game game){
        System.out.printf("Cheese collected: %d out of %d\n",game.cheeseObtained, game.cheeseToWin);
    }

    private static void handleOptions(String option){
        switch (option.toLowerCase()) {
            case "w" -> game.handlePlayerMove(UP);
            case "a" -> game.handlePlayerMove(LEFT);
            case "s" -> game.handlePlayerMove(DOWN);
            case "d" -> game.handlePlayerMove(RIGHT);
            case "?" -> MenuHelper.printHelp();
            case "m" -> mazeDisplay.setMapRevealed(!mazeDisplay.isMapRevealed());
            case "c" -> {
                game.setCheat();
                System.out.println("Cheat set! (don't tell ur friend)");
            }
        }
    }
}
