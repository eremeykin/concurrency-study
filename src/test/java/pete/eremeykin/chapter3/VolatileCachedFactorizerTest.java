package pete.eremeykin.chapter3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pete.eremeykin.chapter2.CachedFactorizer;
import pete.eremeykin.chapter2.Factorizer;
import pete.eremeykin.chapter2.SynchronizeFactorizer;
import pete.eremeykin.utils.PrimeUtils;
import pete.eremeykin.utils.ThreadUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class VolatileCachedFactorizerTest {
    private static final int NUMBER_OF_THREADS = 10;
    private static final int NUMBER_OF_CALCULATIONS = 500_000;
    private static final int MAX_ARG = 1_000_000_000;
    private static final Random random = new Random(234234L);

    private List<Factorizer> factorizers;

    @BeforeEach
    void setUp() {
        factorizers = List.of(
                new CachedFactorizer(),
                new SynchronizeFactorizer(),
                new VolatileCachedFactorizer()
        );
    }


    @Test
    void cachedFactorizerShouldOutperformSynchronizedFactorizer() {
        List<Integer> taskNumbers = generateInput();
        List<ExecutorResult> results = factorizers.stream().map((factorizer) ->
                getFactorizerResult(factorizer, taskNumbers)
        ).toList();
        for (ExecutorResult result : results) {
            System.out.println(result.factorizer.getClass().getSimpleName() + " done in " + ((double) result.timeDiff) / 1_000_000_000 + " s");
            boolean noMistake = IntStream.range(0, taskNumbers.size()).allMatch((i) -> {
                        Integer taskInput = taskNumbers.get(i);
                        List<Integer> taskResult = result.mergedResult.get(i);
                        return taskInput == PrimeUtils.product(taskResult).intValueExact();
                    }
            );
            Assertions.assertTrue(noMistake);
        }
    }

    private static ExecutorResult getFactorizerResult(Factorizer factorizer, List<Integer> taskNumbers) {
        AtomicInteger index = new AtomicInteger(0);
        List<Executor> executors = IntStream.range(0, NUMBER_OF_THREADS)
                .mapToObj((i) -> new Executor(index, taskNumbers, factorizer))
                .collect(Collectors.toList());
        long startTime = System.nanoTime();
        ThreadUtils.fireAtOnce(executors);
        long endTime = System.nanoTime();
        Map<Integer, List<Integer>> mergedResult = Executor.collectResults(executors);
        return new ExecutorResult(factorizer, endTime - startTime, mergedResult);
    }

    private record ExecutorResult(
            Factorizer factorizer,
            long timeDiff,
            Map<Integer, List<Integer>> mergedResult) {
    }

    private static class Executor implements Runnable {
        private final Map<Integer, List<Integer>> taskIndexToResultMap = new ConcurrentHashMap<>();
        private final AtomicInteger index;
        private final List<Integer> taskNumbers;
        private final Factorizer factorizer;

        public Executor(AtomicInteger index,
                        List<Integer> taskNumbers,
                        Factorizer factorizer
        ) {
            this.index = index;
            this.taskNumbers = taskNumbers;
            this.factorizer = factorizer;
        }

        static Map<Integer, List<Integer>> collectResults(List<Executor> executors) {
            Map<Integer, List<Integer>> result = new HashMap<>();
            for (Executor executor : executors) {
                result.putAll(executor.taskIndexToResultMap);
            }
            return result;
        }

        @Override
        public void run() {
            int taskIndex;
            while ((taskIndex = index.getAndIncrement()) < taskNumbers.size()) {
                Integer taskNumber = taskNumbers.get(taskIndex);
                List<Integer> result = factorizer.service(BigInteger.valueOf(taskNumber));
                taskIndexToResultMap.put(taskIndex, result);
            }
        }
    }

    private static List<Integer> generateInput() {
        List<Integer> taskNumbers = new ArrayList<>(NUMBER_OF_CALCULATIONS);
        for (int i = 0; i < NUMBER_OF_CALCULATIONS; i++) {
            boolean hasPrevious = i > 0;
            boolean recalculatePrevious = hasPrevious && random.nextDouble() > 0.9;
            Integer numberToAdd;
            if (recalculatePrevious) {
                numberToAdd = taskNumbers.get(i - 1);
            } else {
                numberToAdd = random.nextInt(0, MAX_ARG);
            }
            taskNumbers.add(numberToAdd);
        }
        return taskNumbers;
    }
}