package edu.poli.efrem.microbenchmark.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimeFactors {
    public static List<Integer> primeFactors(int number) {
        int n = number;
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }

    public void execute() {
        Random newRandom = new Random();
        for (int i = 0; i < 10000; i++) {
            for (Integer integer : primeFactors(Math.abs(newRandom.nextInt(10000)))) {
                System.out.println(integer);
            }
        }
    }
}