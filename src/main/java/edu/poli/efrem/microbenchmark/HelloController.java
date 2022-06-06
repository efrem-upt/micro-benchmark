package edu.poli.efrem.microbenchmark;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class HelloController {
    @FXML
    private Label statusText;
    @FXML
    private ProgressBar benchmarkProgress;

    @FXML
    protected void onHelloButtonClick() {
        statusText.setText("Status: it works");
    }
}