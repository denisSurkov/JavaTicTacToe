package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {
    private static final int MAX_WIDTH = 640;
    private static final int MAX_HEIGHT = 440;

    private static final String RESOURCE_LOCATION = "sample.fxml";
    private static final String WINDOW_TITLE = "Tic Tac Toe Game";

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE_LOCATION));

        Parent root = loader.load();
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(new Scene(root, MAX_WIDTH, MAX_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
