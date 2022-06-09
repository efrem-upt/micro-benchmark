package edu.poli.efrem.microbenchmark.types;


// Java code to find the Kronecker Product of
// two matrices and stores it as matrix C
import org.ejml.simple.SimpleMatrix;
import org.ejml.UtilEjml;

import java.io.*;
import java.util.*;

public class TensorProduct {

    public void execute() {
        Random random = new Random();
        SimpleMatrix A = new SimpleMatrix(64, 64);
        for (int i = 0; i < 64 * 64; i++)
            A.set(i, random.nextInt(20) - 10);
        SimpleMatrix B = new SimpleMatrix(64, 64);
        for (int i = 0; i < 64 * 64; i++)
            B.set(i, random.nextInt(20) - 10);
        SimpleMatrix C = A.kron(B);
        SimpleMatrix D = new SimpleMatrix(64, 64);
        for (int i = 0; i < 64 * 64; i++)
            A.set(i, random.nextDouble(20) - 10);
        SimpleMatrix E = new SimpleMatrix(64, 64);
        for (int i = 0; i < 64 * 64; i++)
            B.set(i, random.nextDouble(20) - 10);
        SimpleMatrix F = D.kron(E);
    }
}