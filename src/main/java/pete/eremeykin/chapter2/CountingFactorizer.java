package pete.eremeykin.chapter2;

import pete.eremeykin.Listing;
import pete.eremeykin.PrimeCalculator;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Listing("2.4")
class CountingFactorizer implements Factorizer {

    private final AtomicLong count = new AtomicLong();

    @Override
    public List<Integer> service(BigInteger n) {
        List<Integer> result = new PrimeCalculator().calculate(n);
        count.incrementAndGet();
        return result;
    }

    public long getCount() {
        return count.get();
    }

}
