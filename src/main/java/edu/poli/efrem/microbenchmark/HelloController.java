package edu.poli.efrem.microbenchmark;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Random;

public class HelloController {
    @FXML
    private Label statusText;
    @FXML
    private ProgressBar benchmarkProgress;
    @FXML
    private Button startButton;
    @FXML
    private Button showResultsButton;

    ArrayList<Result> results;

    @FXML
    public void initialize() {
        showResultsButton.setVisible(false);
    }

    @FXML
    protected void onHelloButtonClick() throws InterruptedException {
        Thread mainProgramThread = new Thread() {
            public void run() {
                Thread welcomeThread = new Thread() {
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                statusText.setText("Status: Starting benchmark..");
                                showResultsButton.setVisible(false);
                                results = new ArrayList<>();
                            }
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                welcomeThread.start();
                try {
                    welcomeThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ThreadMXBean mx1 = ManagementFactory.getThreadMXBean();
                ArrayList<Double> cpuTimeQueen = new ArrayList<Double>();
                ArrayList<Double> cpuTimeFFT = new ArrayList<Double>();
                ArrayList<Double> cpuTimePrime = new ArrayList<Double>();
                benchmarkProgress.setProgress(0);
                Thread operationsThread = new Thread() {
                    public void run() {
                        Thread queenThread =  new Thread() {
                            public void run() {
                                for (int i = 0; i < 500; i++) {
                                    try {
                                        Thread.sleep(15);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Thread queenOperation = new Thread() {
                                        public void run() {
                                            NQueenProblem queenProblem = new NQueenProblem();
                                            queenProblem.solveNQ();
                                            cpuTimeQueen.add((double) mx1.getThreadCpuTime(this.getId()));
                                        }
                                    };
                                    queenOperation.start();
                                    try {
                                        queenOperation.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    int finalI = i;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 0.002/3);
                                            statusText.setText("Status: Running 9 queens problem (backtracking) " + (finalI + 1) + " out of 500");
                                        }
                                    });
                                }
                            }
                        };

                        queenThread.start();
                        try {
                            queenThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Thread fftThread =  new Thread() {
                            public void run() {
                                for (int i = 0; i < 500; i++) {
                                    try {
                                        Thread.sleep(15);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Thread fftOperation = new Thread() {
                                        public void run() {
                                            FFT newFFT = new FFT();
                                            newFFT.execute();
                                            cpuTimeFFT.add((double) mx1.getThreadCpuTime(this.getId()));
                                        }
                                    };
                                    fftOperation.start();
                                    int finalI = i;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 0.002/3);
                                            statusText.setText("Status: Running Fast Fourier Transform " + (finalI + 1) + " out of 500");
                                        }
                                    });
                                }
                            }
                        };

                        fftThread.start();
                        try {
                            fftThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Thread primeThread =  new Thread() {
                            public void run() {
                                for (int i = 0; i < 500; i++) {
                                    try {
                                        Thread.sleep(15);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Thread primeOperation = new Thread() {
                                        public void run() {
                                            PrimeFactors factors = new PrimeFactors();
                                            factors.execute();
                                            cpuTimePrime.add((double) mx1.getThreadCpuTime(this.getId()));
                                        }
                                    };
                                    primeOperation.start();
                                    try {
                                        primeOperation.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    int finalI = i;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 0.002/3);
                                            statusText.setText("Status: Running factorization algorithm " + (finalI + 1) + " out of 500");
                                        }
                                    });
                                }
                            }
                        };

                        primeThread.start();
                        try {
                            primeThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                operationsThread.start();
                Thread afterOperationsThread = new Thread() {
                    public void run() {
                        try {
                            operationsThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        DoubleSummaryStatistics st1 = cpuTimeQueen.stream().mapToDouble(a -> a).summaryStatistics();
                        DoubleSummaryStatistics st2 = cpuTimeFFT.stream().mapToDouble(a -> a).summaryStatistics();
                        DoubleSummaryStatistics st3 = cpuTimePrime.stream().mapToDouble(a -> a).summaryStatistics();
                        Result newResult1 = new Result();
                        newResult1.setResultName("9 Queens Problem (Backtracking)");
                        newResult1.setCpuTimeMeasured(st1.getAverage());
                        results.add(newResult1);
                        Result newResult2 = new Result();
                        newResult2.setResultName("Fast Fourier Transform");
                        newResult2.setCpuTimeMeasured(st2.getAverage());
                        results.add(newResult2);
                        Result newResult3 = new Result();
                        newResult3.setResultName("Factorization algorithm");
                        newResult3.setCpuTimeMeasured(st3.getAverage());
                        results.add(newResult3);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                statusText.setText("Status: Process completed succesfully");
                                showResultsButton.setVisible(true);
                            }
                        });
                    }
                };
                afterOperationsThread.start();
            }
        };
        mainProgramThread.start();
    }

    @FXML
    protected void onShowResultsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("micro-results.fxml"));
        Parent resultsParent = loader.load();
        ResultsController newController = loader.getController();
        newController.setResults(results);
        Scene scene = new Scene(resultsParent);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.setTitle("Dragoș's Micro-Benchmark app - Performance Results");
        newStage.getIcons().add(new Image("file:docs/Logo.png"));
        newStage.show();
        newStage.setResizable(false);
    }
}


