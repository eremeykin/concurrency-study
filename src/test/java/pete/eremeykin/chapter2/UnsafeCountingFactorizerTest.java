package pete.eremeykin.chapter2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pete.eremeykin.utils.ThreadUtils;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class UnsafeCountingFactorizerTest {

    private final Random random = new Random(1345L);
    private UnsafeCountingFactorizer unsafeCountingFactorizer;

    @BeforeEach
    void setUp() {
        unsafeCountingFactorizer = new UnsafeCountingFactorizer();
    }

    @Test
    void shouldFailConcurrencyTest() {
        int calculationsPerThread = 1000;
        int numberOfThreads = 10;
        ThreadUtils.fireAtOnce(
                IntStream.range(0, numberOfThreads).mapToObj(index -> (Runnable) () -> {
                    for (int i = 0; i < calculationsPerThread; i++) {
                        int number = random.nextInt(10, Integer.MAX_VALUE);
                        unsafeCountingFactorizer.service(BigInteger.valueOf(number));
                    }
                }).collect(Collectors.toList()));
        long sequential = calculationsPerThread * numberOfThreads;
        long concurrent = unsafeCountingFactorizer.getCount();
        System.out.println("sequential = " + sequential + "\n" +
                           "concurrent = " + concurrent + "\n");
        Assertions.assertNotEquals(sequential, unsafeCountingFactorizer.getCount());
    }
}