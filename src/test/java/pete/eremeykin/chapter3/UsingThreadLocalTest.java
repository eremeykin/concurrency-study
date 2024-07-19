package pete.eremeykin.chapter3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pete.eremeykin.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class UsingThreadLocalTest {
    private static final int NUMBER_OF_THREADS = 10;
    private static final int NUMBER_OF_ITERATIONS = 10_000;


    @Test
    void eachTreadShouldSeeOwnValues() {
        List<Executor> executors = IntStream
                .range(0, NUMBER_OF_THREADS)
                .mapToObj(Executor::new)
                .toList();
        ThreadUtils.fireAtOnce(executors);
        int countOfZeros = 1;
        long uniqueSize = executors.stream()
                .flatMap((executor -> executor.result.stream()))
                .distinct()
                .count() + countOfZeros;
        Assertions.assertEquals(NUMBER_OF_ITERATIONS, uniqueSize);
    }


    private static class Executor implements Runnable {
        private final List<Integer> result = new ArrayList<>();
        private final int threadIndex;

        public Executor(int threadIndex) {
            this.threadIndex = threadIndex + 1;
        }

        @Override
        public void run() {
            for (int i = threadIndex; i < NUMBER_OF_ITERATIONS; i += NUMBER_OF_THREADS) {
                UsingThreadLocal.setNum(i);
                result.add(UsingThreadLocal.getNum());
            }
        }
    }


}