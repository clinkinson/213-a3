package Maze;

public class MazeCell {
    /**
     * MazeCell is the object type that makes up the maze grid,
     * storing multiple states, such as if the cell is movable or
     * not, if it has been visited on generation, if it has been
     * visible during playtime, etc.
     */
    public boolean movable = true;
    public boolean algorithmVisited;
    public boolean playerVisited;
    public boolean border;
    public boolean isCheese;
    public boolean visible;
    public boolean isCat;
    public boolean isPlayer;


    public boolean isBorder() {
        return border;
    }

    public void setBorder() {
        setBorder(true);
        setVisible(true);
        setMovable(false);
    }

    public boolean isCheese() {
        return isCheese;
    }

    public void setCheese(boolean cheese) {
        isCheese = cheese;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isCat() {
        return isCat;
    }

    public void setCat(boolean cat) {
        isCat = cat;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isAlgorithmVisited() {
        return algorithmVisited;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    @Override
    public String toString() {
        if (isPlayer()) {
            return "!";
        } else if (isCat()) {
            return "@";
        } else if (!isMovable()) {
            return "#";
        } else if (!isVisible()) {
            return ".";
        } else {
            return " ";
        }
    }

    public void setAlgorithmVisited(boolean algorithmVisited) {
        this.algorithmVisited = algorithmVisited;
    }

    public boolean isPlayerVisited() {
        return playerVisited;
    }

    public void setPlayerVisited(boolean playerVisited) {
        this.playerVisited = playerVisited;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }
}
