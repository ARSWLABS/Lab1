package edu.eci.arsw.math;

import java.util.ArrayList;
import java.util.List;

public class PiDigits {

    private static final int DigitsPerSum = 8;
    private static final double Epsilon = 1e-17;

    public static byte[] getDigits(int start, int count, int numThreads) {
        if (start < 0 || count < 0 || numThreads < 1) {
            throw new IllegalArgumentException("Parámetros inválidos");
        }

        byte[] digits = new byte[count];
        List<PiDigitCalculator> threads = new ArrayList<>();

        int chunkSize = count / numThreads; // División equitativa del trabajo
        int remainder = count % numThreads; // Resto a distribuir

        int currentStart = start;
        for (int i = 0; i < numThreads; i++) {
            int currentCount = chunkSize + (i < remainder ? 1 : 0); // Distribuye el resto
            PiDigitCalculator thread = new PiDigitCalculator(currentStart, currentCount, digits, currentStart - start);
            threads.add(thread);
            thread.start();
            currentStart += currentCount;
        }

        for (PiDigitCalculator thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return digits;
    }

    public static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;
            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) break;
            }
            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) power *= 2;

        int result = 1;
        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }
            power /= 2;
            if (power > 0) {
                result *= result;
                result %= m;
            }
        }
        return result;
    }
}
