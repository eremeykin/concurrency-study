package pete.eremeykin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pete.eremeykin.utils.PrimeUtils.product;

class PrimeCalculatorPropertyTest {
    private static final int NUMBER_OF_TRIALS = 10_000;
    private static final int MAX_ARG = 1_000_000_000;
    private static final Random random = new Random(234234L);


    static Stream<Arguments> arguments() {
        return IntStream.range(0, NUMBER_OF_TRIALS).mapToObj((i) ->
                Arguments.of(random.nextInt(MAX_ARG))
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void propertyTest(int n) {
        BigInteger number = BigInteger.valueOf(n);
        List<Integer> factors = new PrimeCalculator().calculate(number);
        Assertions.assertEquals(product(factors), number);
    }
}