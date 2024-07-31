package pete.eremeykin.chapter2;

import pete.eremeykin.Listing;
import pete.eremeykin.PrimeCalculator;

import java.math.BigInteger;
import java.util.List;

@Listing("2.6")
public class SynchronizeFactorizer implements Factorizer {
    private BigInteger lastNumber;
    private List<Integer> lastFactors;


    @Override
    public synchronized List<Integer> service(BigInteger n) {
        if (n.equals(lastNumber)) {
            return lastFactors;
        } else {
            List<Integer> factors = new PrimeCalculator().calculate(n);
            this.lastNumber = n;
            lastFactors = factors;
            return factors;
        }
    }
}
