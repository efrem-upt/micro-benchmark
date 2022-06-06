package edu.poli.efrem.microbenchmark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("micro-welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        scene.getRoot().requestFocus();
        stage.setTitle("Dragoș's Micro-Benchmark app");
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}