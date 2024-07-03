package pete.eremeykin.chapter2;

import pete.eremeykin.Listing;
import pete.eremeykin.PrimeCalculator;
import pete.eremeykin.ThreadSafe;

import java.math.BigInteger;
import java.util.List;

@Listing("2.2")
@ThreadSafe
class UnsafeCountingFactorizer implements Factorizer {

    private long count = 0;

    public List<Integer> service(BigInteger n) {
        List<Integer> result = new PrimeCalculator().calculate(n);
        count++;
        return result;
    }


    public long getCount() {
        return count;
    }
}
