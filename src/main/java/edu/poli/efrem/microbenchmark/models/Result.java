package edu.poli.efrem.microbenchmark.models;

public class Result {
    private String resultName;
    private double cpuTimeMeasured;
    private double cpuTimeMax;
    private double cpuTimeTotal;
    public double getCpuTimeTotal() {
        return cpuTimeTotal;
    }

    public void setCpuTimeTotal(double cpuTimeTotal) {
        this.cpuTimeTotal = cpuTimeTotal;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public double getCpuTimeMeasured() {
        return cpuTimeMeasured;
    }

    public void setCpuTimeMeasured(double cpuTimeMeasured) {
        this.cpuTimeMeasured = cpuTimeMeasured;
    }

    public double getCpuTimeMax() {
        return cpuTimeMax;
    }

    public void setCpuTimeMax(double cpuTimeMax) {
        this.cpuTimeMax = cpuTimeMax;
    }
}