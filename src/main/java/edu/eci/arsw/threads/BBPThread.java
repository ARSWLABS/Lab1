package edu.eci.arsw.threads;
import java.math.BigDecimal;
import java.math.MathContext;

public class BBPThread extends Thread {
    private final int startIndex;
    private final int endIndex;
    private final BigDecimal[] results;

    public BBPThread(int startIndex, int endIndex, BigDecimal[] results) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.results = results;
    }

    @Override
    public void run() {
        for (int k = startIndex; k < endIndex; k++) {
            results[k] = bbpTerm(k);
        }
    }

    private BigDecimal bbpTerm(int k) {
        MathContext mc = new MathContext(50);
        BigDecimal sixteenK = BigDecimal.valueOf(16).pow(k);
        
        BigDecimal term1 = BigDecimal.valueOf(4).divide(BigDecimal.valueOf(8 * k + 1), mc);
        BigDecimal term2 = BigDecimal.valueOf(2).divide(BigDecimal.valueOf(8 * k + 4), mc);
        BigDecimal term3 = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(8 * k + 5), mc);
        BigDecimal term4 = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(8 * k + 6), mc);

        BigDecimal sum = term1.subtract(term2).subtract(term3).subtract(term4);
        return sum.divide(sixteenK, mc);
    }
}
