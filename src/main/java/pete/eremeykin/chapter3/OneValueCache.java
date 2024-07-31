package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

import java.math.BigInteger;
import java.util.Arrays;

@Listing("3.12")
class OneValueCache {
    BigInteger lastNumber;
    Integer[] lastFactors;

    public OneValueCache(BigInteger i, Integer[] factors) {
        this.lastNumber = i;
        if (factors == null) this.lastFactors = null;
        else {
            this.lastFactors = Arrays.copyOf(factors, factors.length);
        }
    }

    public Integer[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i) || lastFactors == null) {
            return null;
        } else {
            return Arrays.copyOf(lastFactors, lastFactors.length);
        }
    }
}
