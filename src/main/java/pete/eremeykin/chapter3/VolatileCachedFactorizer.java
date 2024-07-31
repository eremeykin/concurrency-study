package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;
import pete.eremeykin.PrimeCalculator;
import pete.eremeykin.chapter2.Factorizer;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Listing("3.13")
class VolatileCachedFactorizer implements Factorizer {
    // NPE
    private volatile OneValueCache cache = new OneValueCache(null, null);

    @Override
    public List<Integer> service(BigInteger n) {
        Integer[] factors = cache.getFactors(n);
        if (factors == null) {
            factors = new PrimeCalculator().calculate(n).toArray(Integer[]::new);
            cache = new OneValueCache(n, factors);
        } else {
            System.out.println("Hit");
        }
        return Arrays.stream(factors).toList();
    }
}
