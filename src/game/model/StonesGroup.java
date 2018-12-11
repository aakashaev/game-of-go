package game.model;


import java.util.HashSet;
import java.util.Set;

public class StonesGroup {

    private final Color color;
    private final Set<Point> points;

    public StonesGroup(final Color color, final Point point) {
        this.color = color;
        this.points = new HashSet<>();
        points.add(point);
    }

    public boolean contains(final Point point) {
        for (Point p : points) {
            if (p.equals(point)) return true;
        }
        return false;
    }

    public void add(Point point) {
        points.add(point);
    }

    public Color getColor() {
        return color;
    }

    public Set<Point> getPoints() {
        return points;
    }

    public void clear() {
        points.clear();
    }

}
