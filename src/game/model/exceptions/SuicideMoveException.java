package game.model.exceptions;

public class SuicideMoveException extends GameExceptions {

    public SuicideMoveException() {
        super("Not a valid move! The suicide move.");
    }

}
