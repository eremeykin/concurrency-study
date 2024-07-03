package pete.eremeykin.utils;

import java.math.BigInteger;
import java.util.List;

public class PrimeUtils {
    public static BigInteger product(List<Integer> factors) {
        BigInteger result = BigInteger.ONE;
        for (Integer factor : factors) {
            result = result.multiply(BigInteger.valueOf(factor));
        }
        return result;
    }
}
