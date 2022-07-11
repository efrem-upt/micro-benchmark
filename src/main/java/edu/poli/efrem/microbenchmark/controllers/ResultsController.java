package edu.poli.efrem.microbenchmark.controllers;

import edu.poli.efrem.microbenchmark.models.Result;
import edu.poli.efrem.microbenchmark.services.FirebaseService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class ResultsController {
    private ArrayList<Result> results;
    private ArrayList<Double> cpuTimeAverages;
    private double totalCPUTime;
    private double totalBenchmark;
    private double score;
    private boolean internet;
    private boolean wasPreviouslyFalseInternet;
    @FXML
    private TableView resultsTable;
    @FXML
    private Text cpuTimeMeasuredTotal;
    @FXML
    private Text totalBenchmarkTime;
    @FXML
    private Text finalScoreText;
    @FXML
    private VBox mainBox;
    @FXML
    private BorderPane borderP;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
        resultsTable.setItems(FXCollections.observableList(results));
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
        if (internet == false) {
            finalScoreText.setVisible(false);
            borderP.setVisible(false);
            wasPreviouslyFalseInternet = true;
        } else {
            finalScoreText.setVisible(true);
            borderP.setVisible(true);
            FirebaseService.updateResults(totalCPUTime);
            wasPreviouslyFalseInternet = false;
        }
    }

    public void setCpuTimeAverages(ArrayList<Double> cpuTimeAverages) {
        this.cpuTimeAverages = cpuTimeAverages;
        score = calculateScore();
        if (score == 100)
            finalScoreText.setText("Your score is: " + score + " / 100.0. You're the best -- good job!");
        else if (score < 100 && score >= 75)
            finalScoreText.setText("Your score is: " + score + " / 100.0. That's pretty good!");
        else if (score < 75 && score >= 50)
            finalScoreText.setText("Your score is: " + score + " / 100.0. So close!");
        else if (score < 50)
            finalScoreText.setText("Your score is: " + score + " / 100.0. It will get better!");
    }

    public void setTotalCPUTime(double totalCPUTime) {
        this.totalCPUTime = totalCPUTime;
        cpuTimeMeasuredTotal.setText("CPU Time Average measured for all tasks: " + BigDecimal.valueOf(totalCPUTime).setScale(6, RoundingMode.HALF_EVEN).doubleValue() + " ns");
    }

    public void setTotalBenchmark(double totalBenchmark) {
        this.totalBenchmark = totalBenchmark;
        totalBenchmarkTime.setText("Total benchmark time: " + totalBenchmark + " ns");
    }

    @FXML
    public void initialize() {
        TableColumn<Result, String> nameColumn = new TableColumn<>("Type");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("resultName"));
        TableColumn<Result, Double> timeColumn = new TableColumn<>("CPU Time Average (ns)");
        timeColumn.setMinWidth(200);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("cpuTimeMeasured"));
        TableColumn<Result, Double> timeMaxColumn = new TableColumn<>("CPU Time Max (ns)");
        timeMaxColumn.setMinWidth(200);
        timeMaxColumn.setCellValueFactory(new PropertyValueFactory<>("cpuTimeMax"));
        TableColumn<Result, Double> timeTotalColumn = new TableColumn<>("CPU Time Total (ns)");
        timeTotalColumn.setMinWidth(200);
        timeTotalColumn.setCellValueFactory(new PropertyValueFactory<>("cpuTimeTotal"));
        resultsTable.getColumns().addAll(nameColumn, timeColumn, timeMaxColumn, timeTotalColumn);
    }


    public double calculateScore() {
        Collections.sort(cpuTimeAverages);
        double poz = 0;
        int ok = 0;
        int found = 0;
        for (int i = 0; i < cpuTimeAverages.size(); i++)
            if (totalCPUTime < cpuTimeAverages.get(i)) {
                poz = i;
                ok = 1;
                break;
            } else if (totalCPUTime == cpuTimeAverages.get(i)) {
                poz = i;
                ok = 1;
                found = 1;
                break;
            }
        if (ok == 0 && cpuTimeAverages.size() > 0)
            poz = cpuTimeAverages.size() - 1;

        int newSize = (found == 0) ? (cpuTimeAverages.size() + 1) : (cpuTimeAverages.size());
        double returnedScore = (1 - (poz / newSize)) * 100;
        return BigDecimal.valueOf(returnedScore).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

}