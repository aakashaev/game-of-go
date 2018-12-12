package game.model;


import java.util.ArrayList;

public class Game {

    private final Player blackPlayer;
    private final Player whitePlayer;
    private final Field field;

    private final int handicap;
    private final double komi;

    private final ArrayList<Field> history = new ArrayList<>();

    private Game(final Player blackPlayer,
                 final Player whitePlayer,
                 final Field field,
                 final int handicap) {
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        this.field = field;
        this.handicap = handicap;
        this.komi = handicap > 1 ? 0.5 : 6.5;
    }

    public Field getField() {
        return field;
    }

    public int getHandicap() {
        return handicap;
    }

    public double getKomi() {
        return komi;
    }

    public ArrayList<Field> getHistory() {
        return history;
    }

    public void addToHistory(final Field field) {
        history.add(field);
    }

}
