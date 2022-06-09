package edu.poli.efrem.microbenchmark.controllers;

import edu.poli.efrem.microbenchmark.models.Result;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ResultsController {
    private ArrayList<Result> results;
    private double totalCPUTime;
    private double totalBenchmark;

    @FXML
    private TableView resultsTable;
    @FXML
    private Text cpuTimeMeasuredTotal;
    @FXML
    private Text totalBenchmarkTime;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
        resultsTable.setItems(FXCollections.observableList(results));
    }

    public void setTotalCPUTime(double totalCPUTime) {
        this.totalCPUTime = totalCPUTime;
        cpuTimeMeasuredTotal.setText("CPU Time measured for all tasks: " + totalCPUTime + " ns");
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



}
