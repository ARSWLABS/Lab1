package edu.eci.arsw.math;

import java.util.ArrayList;
import java.util.List;

public class PiDigits {

    private static final int DigitsPerSum = 8;
    private static final double Epsilon = 1e-17;

    /**
     * Calculates the digits of Pi starting from a specific index.
     *
     * @param start The starting index for the calculation.
     * @param count The number of digits to calculate.
     * @param numThreads The number of threads to use for the calculation.
     * @return An array of bytes containing the calculated digits of Pi.
     * @throws IllegalArgumentException if the parameters are invalid.
     */
    public static byte[] getDigits(int start, int count, int numThreads) {
        if (start < 0 || count < 0 || numThreads < 1) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        byte[] digits = new byte[count];
        List<PiDigitCalculator> threads = new ArrayList<>();

        int chunkSize = count / numThreads;
        int remainder = count % numThreads;

        int currentStart = start;
        for (int i = 0; i < numThreads; i++) {
            // Calculate the number of digits to be processed by the current thread
            int currentCount = chunkSize + (i < remainder ? 1 : 0); 
            // Create a new thread to calculate the digits of Pi
            PiDigitCalculator thread = new PiDigitCalculator(currentStart, currentCount, digits, currentStart - start);
            // Add the thread to the list of threads
            threads.add(thread);
            // Start the thread
            thread.start();
            // Update the starting index for the next chunk of digits
            currentStart += currentCount;
        }

        // Wait for all threads to complete
        for (PiDigitCalculator thread : threads) {
            try {
                thread.join(); // Wait for the thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace(); // Print the stack trace if an interruption occurs
            }
        }

        // Return the array of calculated digits
        return digits;
    }

    /**
     * Calculate the sum of a series for Pi digit calculation.
     *
     * @param m The multiplier in the series.
     * @param n The starting exponent in the series.
     * @return The calculated sum.
     */
    public static double sum(int m, int n) {
        double sum = 0; // Initialize the sum
        int d = m; // Initialize the denominator
        int power = n; // Initialize the power

        // Loop to calculate the sum of the series
        while (true) {
            double term;
            if (power > 0) {
                // Calculate the term using hex exponent modulo
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                // Calculate the term using Math.pow
                term = Math.pow(16, power) / d;
                if (term < Epsilon) break; // Break the loop if the term is smaller than epsilon
            }
            sum += term; // Add the term to the sum
            power--; // Decrement the power
            d += 8; // Increment the denominator by 8
        }
        return sum; // Return the calculated sum
    }

    // Placeholder for the hexExponentModulo method
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