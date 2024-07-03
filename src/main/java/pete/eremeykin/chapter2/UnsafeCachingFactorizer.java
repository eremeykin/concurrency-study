package pete.eremeykin.chapter2;

import pete.eremeykin.Listing;
import pete.eremeykin.PrimeCalculator;
import pete.eremeykin.ThreadSafe;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Listing("2.5")
@ThreadSafe
class UnsafeCachingFactorizer implements Factorizer {
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    public /*synchronized*/ List<Integer> service(BigInteger n) {
        if (n.equals(lastNumber.get())) {
            return Arrays.stream(lastFactors.get())
                    .map(BigInteger::intValueExact)
                    .toList();
        } else {
            List<Integer> result = new PrimeCalculator().calculate(n).stream().toList();
            BigInteger[] factors = result.stream()
                    .map(BigInteger::valueOf)
                    .toArray(BigInteger[]::new);
            lastNumber.set(n);
            lastFactors.set(factors);
            return result;
        }
    }
}
