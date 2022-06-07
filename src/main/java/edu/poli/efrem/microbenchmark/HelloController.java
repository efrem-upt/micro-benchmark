package edu.poli.efrem.microbenchmark;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    protected void onHelloButtonClick() throws InterruptedException {
        Thread mainProgramThread = new Thread() {
            public void run() {
                Thread welcomeThread = new Thread() {
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                statusText.setText("Status: Starting benchmark..");
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
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 0.001);
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
                                            benchmarkProgress.setProgress(benchmarkProgress.getProgress() + 0.001);
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
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                statusText.setText("Status: Process completed succesfully");
                            }
                        });
                        System.out.println(st2.getMin() + " " + st2.getMax());
                    }
                };
                afterOperationsThread.start();
            }
        };
        mainProgramThread.start();


    }
}