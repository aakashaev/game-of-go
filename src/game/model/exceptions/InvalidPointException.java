package game.model.exceptions;

import game.model.Point;

public class InvalidPointException extends GameExceptions {

    public InvalidPointException(final Point point){
        super(String.format("Point [%d, %d] not a valid!", point.getX(), point.getY()));
    }

}
