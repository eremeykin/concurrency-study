package pete.eremeykin.chapter2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pete.eremeykin.utils.ThreadUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static pete.eremeykin.utils.PrimeUtils.product;

class UnsafeCachingFactorizerPropertyTest {
    private static final int NUMBER_OF_THREADS = 10;
    private static final int NUMBER_OF_TRIALS = 100_000;
    private static final int MAX_ARG = 1_000_000_000;
    private static final Random random = new Random(234234L);


    private static class IndependentExecutor implements Runnable {
        private final AtomicInteger index;
        private final List<Integer> inputData;
        private final Factorizer factorizer;
        private final Map<Integer, List<Integer>> executorResults = new HashMap<>();
        private final Random recalculateOldRandom = new Random(12345L);

        public IndependentExecutor(AtomicInteger index, List<Integer> inputData, Factorizer factorizer) {
            this.index = index;
            this.inputData = inputData;
            this.factorizer = factorizer;
        }

        @Override
        public void run() {
            int currentThreadIndex = -1;
            while (currentThreadIndex < inputData.size()) {
                boolean recalculateOld = this.recalculateOldRandom.nextDouble() > 0.75 && currentThreadIndex > 0;
                if (!recalculateOld) {
                    // go back sometimes to recalculate old values and provoke a cache hit
                    currentThreadIndex = index.getAndIncrement();
                    if (currentThreadIndex >= inputData.size()) return;
                }
                Integer number = inputData.get(currentThreadIndex);
                List<Integer> factors = factorizer.service(BigInteger.valueOf(number));
                executorResults.put(currentThreadIndex, factors);
            }
        }
    }

    @Test
    void shouldReturnFalseResultSometimes() {
        List<Integer> inputNumbers = IntStream.range(0, NUMBER_OF_TRIALS).mapToObj((i) ->
                random.nextInt(MAX_ARG)
        ).toList();
        Factorizer factorizer = new UnsafeCachingFactorizer();
        AtomicInteger currentIndex = new AtomicInteger(0);
        List<IndependentExecutor> executors = IntStream.range(0, NUMBER_OF_THREADS).mapToObj((i) ->
                        new IndependentExecutor(currentIndex, inputNumbers, factorizer))
                .toList();
        ThreadUtils.fireAtOnce(executors);
        List<List<Integer>> mergedResult = new ArrayList<>(Collections.nCopies(inputNumbers.size(), null));
        for (IndependentExecutor executor : executors) {
            executor.executorResults.forEach(mergedResult::set);
        }
        boolean allProductsEqualToGivenNumber = IntStream.range(0, inputNumbers.size()).allMatch((i) -> {
                    Integer currentInputNumber = inputNumbers.get(i);
                    List<Integer> currentResult = mergedResult.get(i);
                    return currentInputNumber.equals(product(currentResult).intValueExact());
                }
        );
        Assertions.assertFalse(allProductsEqualToGivenNumber);
    }
}