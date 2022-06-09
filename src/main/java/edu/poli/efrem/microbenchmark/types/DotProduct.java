package edu.poli.efrem.microbenchmark.types;

import org.ejml.simple.SimpleMatrix;
import org.ejml.UtilEjml;

import java.util.Random;

public class DotProduct {
    public void execute() {
        Random random = new Random();
        SimpleMatrix A = new SimpleMatrix(1024, 1024);
        for (int i = 0; i < 1024 * 1024; i++)
            A.set(i, random.nextInt(20) - 10);
        SimpleMatrix B = new SimpleMatrix(1024, 1024);
        for (int i = 0; i < 1024 * 1024; i++)
            B.set(i, random.nextInt(20) - 10);
        SimpleMatrix C = A.mult(B);
        SimpleMatrix D = new SimpleMatrix(1024, 1024);
        for (int i = 0; i < 1024 * 1024; i++)
            A.set(i, random.nextDouble(20) - 10);
        SimpleMatrix E = new SimpleMatrix(1024, 1024);
        for (int i = 0; i < 1024 * 1024; i++)
            B.set(i, random.nextDouble(20) - 10);
        SimpleMatrix F = D.mult(E);
    }
}
