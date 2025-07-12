package Characters;
import Coordinate.Coordinate;
public interface Character {
    /**
     * Since both the player and the cat similar functions and fields,
     * such as movement and coordinates, we implemented Character as an
     * Interface to allow for the code to be similar. Plus, there's no
     * nasty or unnecessary inheritance.
     */
    public void moveCharacter(int row, int column);
    public Coordinate getCoordinate();
    public boolean inSamePosition(Character character);
}
