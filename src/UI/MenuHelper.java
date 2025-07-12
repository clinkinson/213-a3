package UI;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Static class that contains helper functions for Menu
 */
public final class MenuHelper {
    /**
     * Function to read and validate user input
     * @param regexPattern Regex Pattern to check input against
     * @param invalidMessage Message to be displayed if user input is incorrect
     * @return  Validated and lower-cased string
     */
    public static String getInput(String regexPattern, String invalidMessage){
        while(true) {
            System.out.println("Enter your move [WASD?]:");
            Scanner scanner = new Scanner(System.in);
            String option;
            try {
                option = scanner.next(regexPattern);
            } catch (NoSuchElementException e) {
                System.out.println(invalidMessage);
                continue;
            }
            return option.toLowerCase();
        }
    }
    public static void printWelcomeMessage(){
        System.out.println(
                """
                ----------------------------------------
                Welcome to Cat and Mouse Maze Adventure!
                by Sarah and Luis!!!!
                ----------------------------------------
                """);
    }

    public static void printHelp(){
        System.out.println(
                """
                DIRECTIONS:
                    Find 5 cheese before a cat eats you!
                LEGEND:
                    #: Wall
                    @: You (a mouse)
                    !: Cat
                    $: Cheese
                    .: Unexplored space
                MOVES:
                    Use W (up), A (left), S (down) and D (right) to move.
                    (You must press enter after each move).
                    
                    Press q to quit!
                """
        );
    }
}
