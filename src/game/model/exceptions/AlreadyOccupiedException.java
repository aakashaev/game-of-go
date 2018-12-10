package game.model.exceptions;

public class AlreadyOccupiedException extends GameExceptions {

    public AlreadyOccupiedException() {
        super("Not a valid cell! The cell is busy.");
    }

}
