package pete.eremeykin.chapter2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pete.eremeykin.ThreadUtils;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CountingFactorizerTest {

    private Random random = new Random(1345L);
    private CountingFactorizer countingFactorizer;

    @BeforeEach
    void setUp() {
        countingFactorizer = new CountingFactorizer();
    }

    @Test
    void shouldFailConcurrencyTest() {
        int calculationsPerThread = 1000;
        int numberOfThreads = 10;
        ThreadUtils.fireAtOnce(
                IntStream.range(0, numberOfThreads).mapToObj(index -> (Runnable) () -> {
                    for (int i = 0; i < calculationsPerThread; i++) {
                        int number = random.nextInt(10, Integer.MAX_VALUE);
                        countingFactorizer.service(BigInteger.valueOf(number));
                    }
                }).collect(Collectors.toList()));
        long sequential = calculationsPerThread * numberOfThreads;
        long concurrent = countingFactorizer.getCount();
        System.out.println("sequential = " + sequential + "\n" +
                           "concurrent = " + concurrent + "\n");
        Assertions.assertEquals(sequential, countingFactorizer.getCount());
    }
}