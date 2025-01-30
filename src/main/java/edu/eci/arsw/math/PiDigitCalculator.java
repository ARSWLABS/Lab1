package edu.eci.arsw.math;

/**
 * Class that calculates the digits of Pi in a specific range.
 * Extends the Thread class to allow parallel execution.
 */
public class PiDigitCalculator extends Thread {
    private final int start; // Starting index for the calculation
    private final int count; // Number of digits to calculate
    private final byte[] resultArray; // Array where the results will be stored
    private final int offset; // Offset in the result array

    /**
     * Constructor for the PiDigitCalculator class.
     *
     * @param start Starting index for the calculation.
     * @param count Number of digits to calculate.
     * @param resultArray Array where the results will be stored.
     * @param offset Offset in the result array.
     */
    public PiDigitCalculator(int start, int count, byte[] resultArray, int offset) {
        this.start = start;
        this.count = count;
        this.resultArray = resultArray;
        this.offset = offset;
    }

    /**
     * Method that performs the calculation of Pi digits.
     * This method is called when the thread is started.
     */
    @Override
    public void run() {
        double sum = 0; // Partial sum for Pi calculation
        int currentStart = start; // Current index for the calculation

        // Loop to calculate the digits of Pi
        for (int i = 0; i < count; i++) {
            // Every 8 iterations, recalculate the partial sum
            if (i % 8 == 0) {
                sum = 4 * PiDigits.sum(1, currentStart)
                    - 2 * PiDigits.sum(4, currentStart)
                    - PiDigits.sum(5, currentStart)
                    - PiDigits.sum(6, currentStart);
                currentStart += 8; // Increment the current index by 8
            }
            // Calculate the next digit of Pi
            sum = 16 * (sum - Math.floor(sum));
            // Store the calculated digit in the result array
            resultArray[offset + i] = (byte) sum;
        }
    }
}