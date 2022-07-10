package edu.poli.efrem.microbenchmark.controllers;

import edu.poli.efrem.microbenchmark.models.Result;
import edu.poli.efrem.microbenchmark.services.FirebaseService;
import edu.poli.efrem.microbenchmark.types.*;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

public class HelloController {
    @FXML
    private Label statusText;
    @FXML
    private ProgressBar benchmarkProgress;
    @FXML
    private Button startButton;
    @FXML
    private Button showResultsButton;
    @FXML
    private Label estimatedTimeRemaining;
    @FXML
    private Label progress;

    ArrayList<Result> results;
    private double totalCPUTime;
    private double totalBenchmark;

    public Button getStartButton() {
        return startButton;
    }

    @FXML
    public void initialize() {
        showResultsButton.setVisible(false);
        estimatedTimeRemaining.setVisible(false);
        progress.setVisible(false);
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
                                startButton.setVisible(false);
                                estimatedTimeRemaining.setVisible(true);
                                estimatedTimeRemaining.setText("Estimated time remaining: ");
                                progress.setVisible(true);
                                progress.setText("0%");
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
                ArrayList<Double> cpuTimeDot = new ArrayList<Double>();
                ArrayList<Double> cpuTimeThread = new ArrayList<Double>();
                long startTime = System.nanoTime();
                benchmarkProgress.setProgress(0);
                Thread operationsThread = new Thread() {
                    public void run() {
                        Thread queenThread =  new Thread() {
                            public void run() {
                                for (int i = 0; i < 100; i++) {
                                    try {
                                        Thread.sleep(15);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Thread queenOperation = new Thread() {
                                        public void run() {
                                            NQueenProblem queenProblem = new NQueenProblem();
                                            queenProblem.solveNQ(1);
                                            queenProblem.solveNQ(2);
                                            queenProblem.solveNQ(3);
                                            queenProblem.solveNQ(4);
                                            queenProblem.solveNQ(5);
                                            queenProblem.solveNQ(6);
                                            queenProblem.solveNQ(7);
                                            queenProblem.solveNQ(8);
                                            queenProblem.solveNQ(9);
                                            queenProblem.solveNQ(10);
                                            queenProblem.solveNQ(11);
                                            queenProblem.solveNQ(12);
                                            queenProblem.solveNQ(13);
                                            queenProblem.solveNQ(14);
                                            queenProblem.solveNQ(15);
                                            queenProblem.solveNQ(16);
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
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 0.01/5);
                                            progress.setText((int)(benchmarkProgress.getProgress() * 100) + "%");
                                            statusText.setText("Status: Running N queens problem (backtracking)");
                                            long elapsedTime = System.nanoTime() - startTime;
                                            double calc = ((elapsedTime / benchmarkProgress.getProgress()) - elapsedTime) * 0.000000001 * 0.0166666667;
                                            long remainingTime;
                                            String unit;
                                            if (calc >= 1) {
                                                remainingTime = Math.round(calc);
                                                if (remainingTime != 1)
                                                    unit = "minutes";
                                                else
                                                    unit = "minute";
                                            } else {
                                                remainingTime = Math.round(calc / 0.0166666667);
                                                if (remainingTime != 1)
                                                    unit = "seconds";
                                                else
                                                    unit = "second";
                                            }
                                            estimatedTimeRemaining.setText("Estimated time remaining: " + remainingTime + " " + unit);
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
                                for (int i = 0; i < 100; i++) {
                                    try {
                                        Thread.sleep(15);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Thread fftOperation = new Thread() {
                                        public void run() {
                                            FFT newFFT = new FFT();
                                            newFFT.execute(32);
                                            newFFT.execute(64);
                                            newFFT.execute(512);
                                            newFFT.execute(1024);
                                            cpuTimeFFT.add((double) mx1.getThreadCpuTime(this.getId()));
                                        }
                                    };
                                    fftOperation.start();
                                    try {
                                        fftOperation.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    int finalI = i;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 0.01/5);
                                            progress.setText((int)(benchmarkProgress.getProgress() * 100) + "%");
                                            statusText.setText("Status: Running Fast Fourier Transform");
                                            long elapsedTime = System.nanoTime() - startTime;
                                            double calc = ((elapsedTime / benchmarkProgress.getProgress()) - elapsedTime) * 0.000000001 * 0.0166666667;
                                            long remainingTime;
                                            String unit;
                                            if (calc >= 1) {
                                                remainingTime = Math.round(calc);
                                                if (remainingTime != 1)
                                                    unit = "minutes";
                                                else
                                                    unit = "minute";
                                            } else {
                                                remainingTime = Math.round(calc / 0.0166666667);
                                                if (remainingTime != 1)
                                                    unit = "seconds";
                                                else
                                                    unit = "second";
                                            }
                                            estimatedTimeRemaining.setText("Estimated time remaining: " + remainingTime + " " + unit);
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
                                for (int i = 0; i < 100; i++) {
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
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 0.01/5);
                                            progress.setText((int)(benchmarkProgress.getProgress() * 100) + "%");
                                            statusText.setText("Status: Running factorization algorithm");
                                            long elapsedTime = System.nanoTime() - startTime;
                                            double calc = ((elapsedTime / benchmarkProgress.getProgress()) - elapsedTime) * 0.000000001 * 0.0166666667;
                                            long remainingTime;
                                            String unit;
                                            if (calc >= 1) {
                                                remainingTime = Math.round(calc);
                                                if (remainingTime != 1)
                                                    unit = "minutes";
                                                else
                                                    unit = "minute";
                                            } else {
                                                remainingTime = Math.round(calc / 0.0166666667);
                                                if (remainingTime != 1)
                                                    unit = "seconds";
                                                else
                                                    unit = "second";
                                            }
                                            estimatedTimeRemaining.setText("Estimated time remaining: " + remainingTime + " " + unit);
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
                        Thread dotThread =  new Thread() {
                            public void run() {
                                for (int i = 0; i < 50; i++) {
                                    try {
                                        Thread.sleep(15);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Thread dotOperation = new Thread() {
                                        public void run() {
                                            DotProduct product = new DotProduct();
                                            product.execute();
                                            cpuTimeDot.add((double) mx1.getThreadCpuTime(this.getId()));
                                        }
                                    };
                                    dotOperation.start();
                                    try {
                                        dotOperation.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    int finalI = i;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 2 * 0.01/5);
                                            progress.setText((int)(benchmarkProgress.getProgress() * 100) + "%");
                                            statusText.setText("Status: Running large matrix dot product");
                                            long elapsedTime = System.nanoTime() - startTime;
                                            double calc = ((elapsedTime / benchmarkProgress.getProgress()) - elapsedTime) * 0.000000001 * 0.0166666667;
                                            long remainingTime;
                                            String unit;
                                            if (calc >= 1) {
                                                remainingTime = Math.round(calc);
                                                if (remainingTime != 1)
                                                    unit = "minutes";
                                                else
                                                    unit = "minute";
                                            } else {
                                                remainingTime = Math.round(calc / 0.0166666667);
                                                if (remainingTime != 1)
                                                    unit = "seconds";
                                                else
                                                    unit = "second";
                                            }
                                            estimatedTimeRemaining.setText("Estimated time remaining: " + remainingTime + " " + unit);
                                        }
                                    });

                                }
                            }
                        };

                        dotThread.start();
                        try {
                            dotThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Thread tensorThread =  new Thread() {
                            public void run() {
                                for (int i = 0; i < 50; i++) {
                                    try {
                                        Thread.sleep(15);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Thread tensorOperation = new Thread() {
                                        public void run() {
                                            TensorProduct product = new TensorProduct();
                                            product.execute();
                                            cpuTimeThread.add((double) mx1.getThreadCpuTime(this.getId()));
                                        }
                                    };
                                    tensorOperation.start();
                                    try {
                                        tensorOperation.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    int finalI = i;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 2 * 0.01/5);
                                            progress.setText((int)(benchmarkProgress.getProgress() * 100) + "%");
                                            statusText.setText("Status: Running large matrix tensor product");
                                            long elapsedTime = System.nanoTime() - startTime;
                                            double calc = ((elapsedTime / benchmarkProgress.getProgress()) - elapsedTime) * 0.000000001 * 0.0166666667;
                                            long remainingTime;
                                            String unit;
                                            if (calc >= 1) {
                                                remainingTime = Math.round(calc);
                                                if (remainingTime != 1)
                                                    unit = "minutes";
                                                else
                                                    unit = "minute";
                                            } else {
                                                remainingTime = Math.round(calc / 0.0166666667);
                                                if (remainingTime != 1)
                                                    unit = "seconds";
                                                else
                                                    unit = "second";
                                            }
                                            estimatedTimeRemaining.setText("Estimated time remaining: " + remainingTime + " " + unit);
                                        }
                                    });
                                }
                            }
                        };

                        tensorThread.start();
                        try {
                            tensorThread.join();
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
                        DoubleSummaryStatistics st4 = cpuTimeDot.stream().mapToDouble(a -> a).summaryStatistics();
                        DoubleSummaryStatistics st5 = cpuTimeThread.stream().mapToDouble(a -> a).summaryStatistics();
                        totalBenchmark = System.nanoTime() - startTime;
                        Result newResult1 = new Result();
                        newResult1.setResultName("N Queens Problem (Backtracking)");
                        newResult1.setCpuTimeMeasured(BigDecimal.valueOf(geometricAverage(cpuTimeQueen)).setScale(6, RoundingMode.HALF_EVEN).doubleValue());
                        newResult1.setCpuTimeMax(st1.getMax());
                        newResult1.setCpuTimeTotal(st1.getSum());
                        results.add(newResult1);
                        Result newResult2 = new Result();
                        newResult2.setResultName("Fast Fourier Transform");
                        newResult2.setCpuTimeMeasured(BigDecimal.valueOf(geometricAverage(cpuTimeFFT)).setScale(6, RoundingMode.HALF_EVEN).doubleValue());
                        newResult2.setCpuTimeMax(st2.getMax());
                        newResult2.setCpuTimeTotal(st2.getSum());
                        results.add(newResult2);
                        Result newResult3 = new Result();
                        newResult3.setResultName("Factorization Algorithm");
                        newResult3.setCpuTimeMeasured(BigDecimal.valueOf(geometricAverage(cpuTimePrime)).setScale(6, RoundingMode.HALF_EVEN).doubleValue());
                        newResult3.setCpuTimeMax(st3.getMax());
                        newResult3.setCpuTimeTotal(st3.getSum());
                        results.add(newResult3);
                        Result newResult4 = new Result();
                        newResult4.setResultName("Large Matrix Dot Product");
                        newResult4.setCpuTimeMeasured(BigDecimal.valueOf(geometricAverage(cpuTimeDot)).setScale(6, RoundingMode.HALF_EVEN).doubleValue());
                        newResult4.setCpuTimeMax(st4.getMax());
                        newResult4.setCpuTimeTotal(st4.getSum());
                        results.add(newResult4);
                        Result newResult5 = new Result();
                        newResult5.setResultName("Large Matrix Tensor Product");
                        newResult5.setCpuTimeMeasured(BigDecimal.valueOf(geometricAverage(cpuTimeThread)).setScale(6, RoundingMode.HALF_EVEN).doubleValue());
                        newResult5.setCpuTimeMax(st5.getMax());
                        newResult5.setCpuTimeTotal(st5.getSum());
                        results.add(newResult5);
                        ArrayList<Double> cpuTimeTotal = new ArrayList<>();
                        cpuTimeTotal.addAll(cpuTimeQueen);
                        cpuTimeTotal.addAll(cpuTimeFFT);
                        cpuTimeTotal.addAll(cpuTimePrime);
                        cpuTimeTotal.addAll(cpuTimeDot);
                        cpuTimeTotal.addAll(cpuTimeThread);
                        totalCPUTime = geometricAverageAll(cpuTimeTotal);
                        if (FirebaseService.isInternetReachable() == true)
                            FirebaseService.updateResults(totalCPUTime);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                statusText.setText("Status: Process completed successfully");
                                showResultsButton.setVisible(true);
                                startButton.setVisible(true);
                                estimatedTimeRemaining.setVisible(false);
                            }
                        });
                    }
                };
                afterOperationsThread.start();
            }
        };
        mainProgramThread.start();
    }

    private double geometricAverage(ArrayList<Double> cpuTime) {
        int n = cpuTime.size();
        double GM_log = 0.0d;
        for (int i = 0; i < n; ++i) {
            if (cpuTime.get(i) == 0)
                continue;
            GM_log += Math.log(cpuTime.get(i));
        }
        return (n > 0) ? Math.exp(GM_log / n) : n;
    }

    private double geometricAverageAll(ArrayList<Double> cpuTime) {
        int n = cpuTime.size();
        double GM_log = 0.0d;
        double w_i;
        for (int i = 0; i < n; ++i) {
            if (cpuTime.get(i) == 0)
                continue;
            if (i < 100) w_i = 0.05 / 100;
            else if (i < 200) w_i = 0.3 / 100;
            else if (i < 300) w_i = 0.1 / 100;
            else if (i < 350) w_i = 0.3 / 50;
            else w_i = 0.25 / 50;
            GM_log += w_i * Math.log(cpuTime.get(i));
        }
        return (GM_log > 0) ? Math.exp(GM_log) : GM_log;
    }

    @FXML
    protected void onShowResultsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("micro-results.fxml"));
        Parent resultsParent = loader.load();
        ResultsController newController = loader.getController();
        newController.setResults(results);
        newController.setTotalCPUTime(totalCPUTime);
        if (FirebaseService.isInternetReachable() == true) {
            newController.setCpuTimeAverages(FirebaseService.returnResults());
            newController.setInternet(true);
        } else {
            newController.setInternet(false);
        }
        newController.setTotalBenchmark(totalBenchmark);
        Scene scene = new Scene(resultsParent);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.setTitle("Dragos's Micro-Benchmark app - Performance Results");
        newStage.getIcons().add(new Image("logo.png"));
        newStage.show();
        newStage.setResizable(false);
    }
}


