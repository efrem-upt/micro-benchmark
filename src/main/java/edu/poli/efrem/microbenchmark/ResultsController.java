package edu.poli.efrem.microbenchmark;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

public class ResultsController {
    private ArrayList<Result> results;

    @FXML
    private TableView resultsTable;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
        resultsTable.setItems(FXCollections.observableList(results));
    }

    @FXML
    public void initialize() {
        TableColumn<Result, String> nameColumn = new TableColumn<>("Type");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("resultName"));
        TableColumn<Result, Double> timeColumn = new TableColumn<>("CPU Time Average (nanoseconds)");
        timeColumn.setMinWidth(200);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("cpuTimeMeasured"));
        TableColumn<Result, Double> timeMaxColumn = new TableColumn<>("CPU Time Max (nanoseconds)");
        timeMaxColumn.setMinWidth(200);
        timeMaxColumn.setCellValueFactory(new PropertyValueFactory<>("cpuTimeMax"));
        TableColumn<Result, Double> timeTotalColumn = new TableColumn<>("CPU Time Total (nanoseconds)");
        timeTotalColumn.setMinWidth(200);
        timeTotalColumn.setCellValueFactory(new PropertyValueFactory<>("cpuTimeTotal"));
        resultsTable.getColumns().addAll(nameColumn, timeColumn, timeMaxColumn, timeTotalColumn);
    }



}
