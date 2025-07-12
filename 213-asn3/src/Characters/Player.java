package Characters;

import Coordinate.Coordinate;

public class Player implements Character {
    /**
     * Just implements Character interface, nothing fancy
     */
    private Coordinate coordinates;
    public Player(Coordinate coordinate){
        coordinates = coordinate;
    }
    @Override
    public void moveCharacter(int row, int column) {
        coordinates = new Coordinate(row, column);
    }
    public void moveCharacter(Coordinate coordinate){
        coordinates = coordinate;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinates;
    }

    @Override
    public boolean inSamePosition(Character character) {
        return coordinates.equals(character.getCoordinate());
    }
}
