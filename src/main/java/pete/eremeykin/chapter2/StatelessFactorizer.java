package pete.eremeykin.chapter2;

import pete.eremeykin.PrimeCalculator;
import pete.eremeykin.ThreadSafe;

import java.math.BigInteger;
import java.util.List;

@ThreadSafe
class StatelessFactorizer implements Factorizer {

    public List<Integer> service(BigInteger n) {
        return new PrimeCalculator().calculate(n);
    }

}
