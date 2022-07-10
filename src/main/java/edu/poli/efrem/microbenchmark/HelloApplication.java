package edu.poli.efrem.microbenchmark;

import edu.poli.efrem.microbenchmark.controllers.HelloController;
import edu.poli.efrem.microbenchmark.services.FirebaseInitialize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    private FXMLLoader loader;
    private Stage stageHello;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("micro-welcome.fxml"));
        loader = fxmlLoader;
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        scene.getRoot().requestFocus();
        stageHello = stage;
        stage.setTitle("Dragos's Micro-Benchmark app");
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setResizable(false);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void closeWindowEvent(WindowEvent event) {
        if (((HelloController)loader.getController()).getStartButton().isVisible() == false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.setTitle("Benchmark is running");
            alert.setContentText(String.format("Close without the benchmark finishing?"));
            alert.initOwner(stageHello.getOwner());
            Optional<ButtonType> res = alert.showAndWait();

            if(res.isPresent()) {
                if(res.get().equals(ButtonType.CANCEL))
                    event.consume();
                else
                    System.exit(2);
            }
        }
    }

    public static void main(String[] args) {
        FirebaseInitialize fire = new FirebaseInitialize();
        fire.initialize();
        launch();
    }
}