package game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsWindow {

    public static void display() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // блокирует взаимодействие с другими окнами
        window.setTitle("Game Settings");
        window.setMinHeight(300);
        window.setMaxWidth(300);

        //-----------------------------------------------------------------
        // выбор игроков
        VBox root = new VBox(10);

        GridPane choicePlayers = new GridPane();
        choicePlayers.setPadding(new Insets(10,10,10,10));
        choicePlayers.setVgap(10);

        Label blackPlayer = new Label("Black Player: ");
        blackPlayer.setMinSize(100, 20);
        GridPane.setConstraints(blackPlayer, 0,0);

        Label whitePlayer = new Label("White Player: ");
        whitePlayer.setMinSize(100, 20);
        GridPane.setConstraints(whitePlayer, 0,1);

        String player1 = "Honinbo Shusaku";
        String player2 = "Go Seigen";
        ObservableList<String> players
                = FXCollections.observableArrayList(player1, player2);

        FlowPane box1 = new FlowPane();
        ChoiceBox<String> choiceBox1 = new ChoiceBox<>(players);
        choiceBox1.getSelectionModel().select(0);
        box1.getChildren().add(choiceBox1);
        GridPane.setConstraints(box1, 1,0);

        FlowPane box2 = new FlowPane();
        ChoiceBox<String> choiceBox2 = new ChoiceBox<>(players);
        choiceBox2.getSelectionModel().select(1);
        box2.getChildren().add(choiceBox2);
        GridPane.setConstraints(box2, 1,1);

        choicePlayers.getChildren().addAll(blackPlayer, whitePlayer, box1, box2);

        //-----------------------------------------------------------------
        // выбор размера доски
        HBox choiseBoardSize = new HBox(20);
        choiseBoardSize.setPadding(new Insets(10,10,10,10));

        Button nine = new Button("9");
        nine.setPrefSize(50,50);
        nine.setFont(new Font("System Regular", 20));

        Button thirteen = new Button("13");
        thirteen.setPrefSize(50,50);
        thirteen.setFont(new Font("System Regular", 20));
        Button nineteen = new Button("19");
        nineteen.setPrefSize(50,50);
        nineteen.setFont(new Font("System Regular", 20));
        nineteen.setDefaultButton(true);

        choiseBoardSize.getChildren().addAll(nine,thirteen,nineteen);


        //-----------------------------------------------------------------
        // выбор гандикапа и коми
        GridPane choiceHandicap = new GridPane();
        choiceHandicap.setPadding(new Insets(10,10,10,10));
        choiceHandicap.setVgap(10);

        Label handicap = new Label("Handicap: ");
        handicap.setMinSize(100, 20);
        GridPane.setConstraints(handicap, 0,0);

        Label komi = new Label("Komi: ");
        komi.setMinSize(100, 20);
        GridPane.setConstraints(komi, 0,1);

        ObservableList<Integer> handicapList
                = FXCollections.observableArrayList(0, 2, 3, 4, 5, 6, 7, 8, 9);

        ObservableList<Double> komiList
                = FXCollections.observableArrayList(0.5, 6.5);

        FlowPane box3 = new FlowPane();
        ChoiceBox<Integer> choiceBox3 = new ChoiceBox<>(handicapList);
        choiceBox3.getSelectionModel().select(0);
        box3.getChildren().add(choiceBox3);
        GridPane.setConstraints(box3, 1,0);

        FlowPane box4 = new FlowPane();
        ChoiceBox<Double> choiceBox4 = new ChoiceBox<>(komiList);
        choiceBox4.getSelectionModel().select(1);
        box4.getChildren().add(choiceBox4);
        GridPane.setConstraints(box4, 1,1);

        choiceHandicap.getChildren().addAll(handicap, komi, box3, box4);

        //-----------------------------------------------------------------
        // выбор размера доски
        HBox confirmButtons = new HBox(20);
        confirmButtons.setPadding(new Insets(10,10,10,10));

        Button runGameButton = new Button("OK!");
        runGameButton.setPrefSize(120,30);
        runGameButton.setFont(new Font("System Regular", 20));
        runGameButton.setOnAction(event -> {
            window.close();
        });

        Button closeButton = new Button("Cancel");
        closeButton.setPrefSize(120,30);
        closeButton.setFont(new Font("System Regular", 20));
        closeButton.setOnAction(event -> window.close());

        confirmButtons.getChildren().addAll(runGameButton, closeButton);

        root.getChildren().addAll(choicePlayers, choiseBoardSize, choiceHandicap, confirmButtons);
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

}
