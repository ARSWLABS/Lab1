package edu.eci.arsw.math;

public class PiDigitCalculator extends Thread {
    private final int start;
    private final int count;
    private final byte[] resultArray;
    private final int offset;

    public PiDigitCalculator(int start, int count, byte[] resultArray, int offset) {
        this.start = start;
        this.count = count;
        this.resultArray = resultArray;
        this.offset = offset;
    }

    @Override
    public void run() {
        double sum = 0;
        int currentStart = start;

        for (int i = 0; i < count; i++) {
            if (i % 8 == 0) {
                sum = 4 * PiDigits.sum(1, currentStart)
                    - 2 * PiDigits.sum(4, currentStart)
                    - PiDigits.sum(5, currentStart)
                    - PiDigits.sum(6, currentStart);
                currentStart += 8;
            }
            sum = 16 * (sum - Math.floor(sum));
            resultArray[offset + i] = (byte) sum;
        }
    }
}
