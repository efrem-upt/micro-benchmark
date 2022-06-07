package edu.poli.efrem.microbenchmark;

public class Result {
    private String resultName;
    private double cpuTimeMeasured;

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
}
