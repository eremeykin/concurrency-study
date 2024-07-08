package pete.eremeykin.chapter2;

import pete.eremeykin.PrimeCalculator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CachedFactorizer implements Factorizer {
    private BigInteger lastNumber;
    private List<Integer> lastFactors;
    private long hits;
    private long cacheHits;

    @Override
    public List<Integer> service(BigInteger n) {
        List<Integer> factors = null;
        synchronized (this) {
            ++hits;
            // check-then-act
            if (n.equals(lastNumber)) {
                ++cacheHits;
                factors = new ArrayList<>(lastFactors);
            }
        }
        if (factors == null) {
            // long stack-local computation, no synchronization requires
            factors = new PrimeCalculator().calculate(n);
            // access shared state, synchronization required
            synchronized (this) {
                lastNumber = n;
                lastFactors = factors;
            }
        }
        return factors;
    }


    // synchronization required, stale data otherwise
    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

}
