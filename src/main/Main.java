package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../grafica/MenuIniziale.fxml"));
        Scene scenaIniziale = new Scene(root,600,400);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scenaIniziale);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
