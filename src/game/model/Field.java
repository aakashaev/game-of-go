package game.model;

import game.model.exceptions.InvalidPointException;


public class Field {

    private final int size;
    private final Stone[][] field;
    private Point lastMove;

    private int whiteCaptured = 0;
    private int blackCaptured = 0;

    public Field(final int size) {
        this.size = size;
        this.field = new Stone[size][size];
    }

    public boolean isOnField(final Point point) {
        return (point.getX() >= 0 && point.getX() < size)
                && (point.getY() >= 0 && point.getY() < size);
    }

    public int getSize() {
        return size;
    }

    public Stone getStone(final Point point) throws InvalidPointException {
        if (!isOnField(point)) {
            throw new InvalidPointException(point);
        }
        return field[point.getX()][point.getY()];
    }

    public void setStone(final Point point, final Stone stone)
            throws InvalidPointException {
        if (!isOnField(point)) {
            throw new InvalidPointException(point);
        }
        field[point.getX()][point.getY()] = stone;
        lastMove = point;
    }

    public Point getLastMove() {
        return lastMove;
    }

    public void setLastMove(final Point move) {
        this.lastMove = move;
    }

    public boolean isEmpty(final Point point) throws InvalidPointException {
        if (!isOnField(point)) {
            throw new InvalidPointException(point);
        }
        return field[point.getX()][point.getY()] == null;
    }

    public void setEmpty(final Point point) throws InvalidPointException {
        if (!isOnField(point)) {
            throw new InvalidPointException(point);
        }
        field[point.getX()][point.getY()] = null;
    }



    public Point[] createHandicapPoints(final int handicap) {
        if (handicap <= 1 || handicap > 9) {
            return null;
        }

        int offset = size > 9 ? 3 : 2;

        Point ld = new Point(offset, size - offset); // left down
        Point ru = new Point(size - offset, offset); // right up
        Point rd = new Point(size - offset, size - offset); // right down
        Point lu = new Point(offset, offset); // left up
        Point center = new Point(size / 2, size / 2); // center
        Point lm = new Point(offset, size / 2); // left middle
        Point rm = new Point(size - offset, size / 2); // right middle
        Point um = new Point(size / 2, offset); // up middle
        Point dm = new Point(size / 2, size - offset); // down middle

        switch (handicap) {
            case 2:
                return new Point[]{ld, ru};
            case 3:
                return new Point[]{ld, ru, rd};
            case 4:
                return new Point[]{ld, ru, rd, lu};
            case 5:
                return new Point[]{ld, ru, rd, lu, center};
            case 6:
                return new Point[]{ld, ru, rd, lu, lm, rm};
            case 7:
                return new Point[]{ld, ru, rd, lu, lm, rm, center};
            case 8:
                return new Point[]{ld, ru, rd, lu, lm, rm, um, dm};
            case 9:
                return new Point[]{ld, ru, rd, lu, lm, rm, um, dm, center};
            default:
                return null;
        }
    }

    public int getWhiteCaptured() {
        return whiteCaptured;
    }

    public int getBlackCaptured() {
        return blackCaptured;
    }

    public void removeCapturedStone(final Point point, final Stone stone) throws InvalidPointException {
        if (stone.getColor() == Color.B) {
            blackCaptured += 1;
        } else if (stone.getColor() == Color.W) {
            whiteCaptured += 1;
        }
        setEmpty(point);
//        System.out.println("remove stone: " + stone + point);
    }

    public void removeCapturedGroups(StonesGroup capturedStones) throws InvalidPointException {
        if (capturedStones != null) {
//            System.out.println("remove group...");
            for (Point p : capturedStones.getPoints()) {
                removeCapturedStone(p, getStone(p));
            }
        }
    }

}
