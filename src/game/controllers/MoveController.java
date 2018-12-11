package game.controllers;

import game.model.*;
import game.model.exceptions.AlreadyOccupiedException;
import game.model.exceptions.InvalidPointException;
import game.model.exceptions.SuicideMoveException;

import java.util.HashSet;
import java.util.Set;

public class MoveController {

    private static final int[] xDirections = {1, 0, -1, 0};
    private static final int[] yDirections = {0, 1, 0, -1};

//    private final Set<StonesGroup> capturedGroups = new HashSet<>();

    // главный метод контроллера - применение камня к игровому полю
    public void applyStone(final Field field, final Point move, final Stone stone) throws AlreadyOccupiedException, InvalidPointException, SuicideMoveException {
        if (!field.isOnField(move)) {
            throw new InvalidPointException(move);
        }
        if (!field.isEmpty(move)) {
            throw new AlreadyOccupiedException();
        }


        Set<Point> neighbours = getNeighbours(field, move);
        Set<StonesGroup> myGroups = new HashSet<>();
        Set<StonesGroup> enemyGroups = new HashSet<>();
        Set<StonesGroup> capturedGroups = new HashSet<>();

        // для каждого из соседей создать группу и поместить в набор
        //int myColorNeighbours = 0;
        for (Point point : neighbours) {
            Color colorInPoint = field.getStone(point).getColor();

            StonesGroup g = createGroup(field, point);

            if (colorInPoint == stone.getColor()) {
                //myColorNeighbours += 1;
                myGroups.add(g);
                System.out.println("add first friend: " + field.getStone(point) + point);
            } else {
                enemyGroups.add(g);
                System.out.println("add first enemy: " + field.getStone(point) + point);
            }
        }

        // суммировать свободы всех моих групп рядом с точкой move
        int myGroupLiberties = 0;
        for (StonesGroup g : myGroups) {
            Set<Point> liberties = getGroupLiberties(field, g);
            System.out.println("my group liberties: " + liberties);
            if (liberties.contains(move)) {
                myGroupLiberties += liberties.size() - 1;
            } else {
                myGroupLiberties += liberties.size();
            }
        }


        // проверить свободы у групп соперника рядом с точкой move
        for (StonesGroup g : enemyGroups) {
            Set<Point> liberties = getGroupLiberties(field, g);
            System.out.println("enemy group liberties: " + liberties);
            if (liberties.contains(move) && liberties.size() == 1) {
                capturedGroups.add(g);
                System.out.println("captured add");
            }
        }
        int numberOfCaptured = capturedGroups.size();

        int moveLiberties = getMoveLiberties(field, move).size();

        if (numberOfCaptured == 0 && myGroupLiberties == 0 && moveLiberties == 0) {
            throw new SuicideMoveException();
        }

        // установка камня
        field.setStone(move, stone);

        // удаление пленных камней
        for (StonesGroup g : capturedGroups) {
            System.out.println("captured stones remove...");
                field.removeCapturedGroups(g);
                System.out.println("Total captured black stones: " + field.getBlackCaptured());
                System.out.println("Total captured white stones: " + field.getWhiteCaptured());
        }

    }

    //--------------------------------------------------------------------
    // создать группу, содержащую камни одного цвета
    private StonesGroup createGroup(final Field field, final Point startPoint) throws InvalidPointException {
        if (field.isEmpty(startPoint)) {
            return null;
        }
        
        Color color = field.getStone(startPoint).getColor();
        StonesGroup group = new StonesGroup(color, startPoint);
        System.out.println("create group " + group.getPoints());
        fillGroup(group, field, startPoint);
        System.out.println("fill group " + group.getPoints());

        return group;
    }

    // заполнение группы через рекурсивный проход по соседям
    private void fillGroup(final StonesGroup group, final Field field, final Point point) throws InvalidPointException {
        for (int i = 0; i < 4; i++) {
            Point p = new Point(point.getX() + xDirections[i], point.getY() + yDirections[i]);

            // если цвет камня в соседней точке совпадает с цветом группы
            // то добавить точку в группу и рекурсивно проверить ее соседей
            try {
                if (!field.isEmpty(p)
                        && !group.contains(p)
                        && field.getStone(p).getColor() == group.getColor()) {
                    group.add(p);
                    fillGroup(group, field, p);
                }
            } catch (InvalidPointException ignore) {}
        }
    }


    // найти количество свобод для текущего хода
    private Set<Point> getMoveLiberties(final Field field, final Point move) {
        Set<Point> libertiesSet = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            try {
                Point p = new Point(move.getX() + xDirections[i], move.getY() + yDirections[i]);
                if (field.isOnField(p) && field.isEmpty(p)) {
                    libertiesSet.add(p);
                }
            } catch (InvalidPointException ignore) {}
        }
        return libertiesSet;
    }

    // найти количество свобод у группы
    private Set<Point> getGroupLiberties(final Field field, final StonesGroup group) {
        Set<Point> libertiesSet = new HashSet<>();
        for (Point point: group.getPoints()) {
            libertiesSet.addAll(getMoveLiberties(field, point));
        }
        return libertiesSet;
    }

    // получить соседей белых и черных
    private Set<Point> getNeighbours(final Field field, final Point move) {
        System.out.println("search neighbours...");
        Set<Point> neighbours = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            try {
                Point p = new Point(move.getX() + xDirections[i], move.getY() + yDirections[i]);
                //System.out.println("point " + i + ": " + p);
                if (!field.isEmpty(p)) {
                    System.out.println("fined! add...");
                    neighbours.add(p);
                }
            } catch (InvalidPointException ignore) {}
        }
        return neighbours;
    }

}
