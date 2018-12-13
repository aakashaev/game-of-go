package game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Scene scene1, scene2, scene3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        root.setMinSize(300, 260);

        VBox buttons = new VBox(20);

        Button onePlayer = new Button("One Player");
        onePlayer.setPrefSize(200,40);
        onePlayer.setOnAction(event -> SettingsWindow.display());

        Button twoPlayers = new Button("Two Players");
        twoPlayers.setPrefSize(200,40);
        twoPlayers.setOnAction(event -> SettingsWindow.display());

        Button exit = new Button("Exit");
        exit.setPrefSize(200,40);
        exit.setOnAction(event -> primaryStage.close());

        buttons.getChildren().addAll(onePlayer, twoPlayers, exit);
        root.setCenter(buttons);
        buttons.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root);

        primaryStage.setTitle("The game of Go");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
