package game;

import game.controllers.MoveController;
import game.model.Color;
import game.model.Field;
import game.model.Point;
import game.model.Stone;
import game.model.exceptions.AlreadyOccupiedException;
import game.model.exceptions.InvalidPointException;
import game.model.exceptions.SuicideMoveException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class GamePlay extends Application {

    private static final int NUM_CELLS = 19;
    private static final int CELL_SIZE = NUM_CELLS > 13 ? 40 : 50;
    private Color currentMove = Color.B;

    private int moveNumber = 1;
    private boolean displayNumbers = false;

    private final Field field = new Field(NUM_CELLS);

    private final MoveController moveController = new MoveController();

    private final Image boardImg = new Image("res/shinkaya.jpg");
    private final Image blackImg = new Image("res/black.png");
    private final Image whiteImg = new Image("res/white.png");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        VBox startMenu = new VBox(10);
        Button onePlayer = new Button("One Player");
        Button twoPlayers = new Button("Two Players");
        Button exit = new Button("Exit");

        // отрисовка доски
        int boardSize = (NUM_CELLS + 2) * CELL_SIZE;
        Canvas board = new Canvas(boardSize, boardSize);
        GraphicsContext gcBoard = board.getGraphicsContext2D();
        drawBoard(gcBoard);

        // отрисовка сетки
        GridPane gridField = new GridPane();
        drawGridField(gridField);
        gridField.setLayoutX(CELL_SIZE);
        gridField.setLayoutY(CELL_SIZE);

        // Добавление доски и сетки в рут
        Group root = new Group();
        root.getChildren().addAll(board, gridField);

        Scene scene = new Scene(root);

        primaryStage.setTitle("The game of Go");
        primaryStage.setScene(scene);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.show();
    }

    //-----------------------------------------------------------------------------------------
    private void changeCurrentMove() {
        currentMove = (currentMove == Color.B) ? Color.W : Color.B;
    }

    private void drawGridField(final GridPane grid) {
        for (int i = 0; i < NUM_CELLS; i++) {
            for (int j = 0; j < NUM_CELLS; j++) {
                Canvas cell = new Canvas(CELL_SIZE, CELL_SIZE);
//                cell.getStyleClass().add("cell");
                cell.setOnMouseClicked(event -> mouseClicked(grid, cell));

                grid.add(cell, i, j);
            }
        }
    }

    private void mouseClicked(final GridPane gridField, final Canvas cell) {
//        System.out.println("Number of move: " + moveNumber);
        int colIndex = GridPane.getColumnIndex(cell);
        int rowIndex = GridPane.getRowIndex(cell);
//        System.out.println("Mouse entered cell [" + colIndex + ", " + rowIndex + "]");
        Point point = new Point(colIndex, rowIndex);
        Stone stone = new Stone(currentMove, moveNumber);
        try {
            moveController.applyStone(field, point, stone);

            drawStones(gridField);

            changeCurrentMove();
            moveNumber++;
            System.out.println(field.getStone(point).getColor());
        } catch (InvalidPointException e) {
            e.printStackTrace();
        } catch (AlreadyOccupiedException e) {
            System.out.println("Point is busy!");
        } catch (SuicideMoveException e) {
            System.out.println("Suicide move!");
        }
    }

    private void drawStone(final GraphicsContext gc, final Color color, final boolean markStone) {
//        System.out.println("draw stone " + stone.getNumber() + stone.getColor());
        Image image = color == Color.B ? blackImg : whiteImg;
        gc.drawImage(image, 0, 0, CELL_SIZE, CELL_SIZE);
        if (markStone) {
            gc.setLineWidth(4);
            gc.setStroke(javafx.scene.paint.Color.GREY);
            gc.strokeOval(CELL_SIZE / 4.0, CELL_SIZE / 4.0, CELL_SIZE / 2, CELL_SIZE / 2);
        }
    }

    private void drawStones(final GridPane grid) throws InvalidPointException {
        for (Node node : grid.getChildren()) {
            Canvas cell = (Canvas) node;
            GraphicsContext gc = cell.getGraphicsContext2D();
            gc.clearRect(0,0, cell.getWidth(), cell.getHeight());

            int colIndex = grid.getColumnIndex(node).intValue();
            int rowIndex = grid.getRowIndex(node).intValue();
            Point point = new Point(colIndex, rowIndex);
            boolean markStone = field.getLastMove().equals(point);
            if (!field.isEmpty(point)) {
                drawStone(gc, field.getStone(point).getColor(), markStone);
            }
        }
    }

    private void drawBoard(final GraphicsContext gc) {
        gc.drawImage(boardImg, 0,0);

        int halfCell = CELL_SIZE / 2;
        int minXY = halfCell + CELL_SIZE;
        int maxXY = halfCell + CELL_SIZE * NUM_CELLS;

        // outside lines
        gc.setLineWidth(3);
        gc.strokeLine(minXY, minXY, maxXY, minXY);
        gc.strokeLine(maxXY, minXY, maxXY, maxXY);
        gc.strokeLine(maxXY, maxXY, minXY, maxXY);
        gc.strokeLine(minXY, maxXY, minXY, minXY);

        // inside lines
        gc.setLineWidth(1);
        for (int i = minXY + CELL_SIZE; i <= maxXY - CELL_SIZE; i += CELL_SIZE) {
            gc.strokeLine(minXY, i, maxXY, i);
            gc.strokeLine(i, minXY, i, maxXY);
        }

        // line labels
        gc.setFont(new Font(20));
        for (int i = 1; i <= NUM_CELLS; i++) {
            // подписи по вертикали
            gc.fillText(String.valueOf(i), halfCell, i * CELL_SIZE + halfCell);
            // подписи по горизонтали (исключая 'I')
            //int sign = i >= 9 ? i + 65: i + 64;
            //gc.fillText(String.valueOf((char)sign), i * 50 - 5, 20);
            gc.fillText(String.valueOf(i), i * CELL_SIZE + halfCell, halfCell);
        }

        // draw points for handicap stones
        int rad = 5;
        int diam = 2 * rad;
        int offset = NUM_CELLS > 9 ? 3 * CELL_SIZE : 2 * CELL_SIZE;
        int minXYOval = minXY + offset - rad;
        int maxXYOval = maxXY - offset - rad;
        int middle = (maxXY - minXY) / 2 + minXY - rad;

        gc.fillOval(middle, middle, diam, diam); // center
        gc.fillOval(minXYOval, minXYOval, diam, diam); // left up
        gc.fillOval(maxXYOval, maxXYOval, diam, diam); // right down
        gc.fillOval(minXYOval, maxXYOval, diam, diam); // left down
        gc.fillOval(maxXYOval, minXYOval, diam, diam); // right up

        if (NUM_CELLS > 10) {
            gc.fillOval(minXYOval, middle, diam, diam); // left middle
            gc.fillOval(maxXYOval, middle, diam, diam); // right middle
            gc.fillOval(middle, minXYOval, diam, diam); // middle up
            gc.fillOval(middle, maxXYOval, diam, diam); // middle down
        }
    }

}


