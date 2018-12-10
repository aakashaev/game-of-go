package game.model;


public class Stone {

    private final Color color;
    private final int number;

    public Stone(final Color color, final int number) {
        this.color = color;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number + "-" + color ;
    }

}
