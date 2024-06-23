package pete.eremeykin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

class PrimeCalculatorTest {

    @Test
    void smokeTest() {
        List<Integer> factors = new PrimeCalculator().calculate(BigInteger.valueOf(12));
        Assertions.assertIterableEquals(List.of(2, 2, 3), factors);
    }

    @Test
    void largeNumberTest() {
        List<Integer> factors = new PrimeCalculator().calculate(BigInteger.valueOf(1234534L));
        System.out.println(factors);
        Assertions.assertIterableEquals(List.of(2, 7, 109, 809), factors);
    }
}