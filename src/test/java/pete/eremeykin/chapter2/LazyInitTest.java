package pete.eremeykin.chapter2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pete.eremeykin.ThreadUtils;
import pete.eremeykin.chapter2.LazyInit.ExpensiveObject;
import pete.eremeykin.chapter2.LazyInit.LazyInitHolderIdiom;
import pete.eremeykin.chapter2.LazyInit.LazyInitRace;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class LazyInitTest {

    public static final int SINGLE = 1;

    static Stream<Arguments> factory() {
        return Stream.of(
                Arguments.of(new LazyInitRace(), false),
                Arguments.of(new LazyInitHolderIdiom(), true)
        );
    }

    @ParameterizedTest
    @MethodSource("factory")
    void testCanGetMultipleInstances(LazyInit objectUnderTest, boolean isThreadSafe) {
        LazyInit lazyInitRace = objectUnderTest;
        Set<ExpensiveObject> createdInstances = ConcurrentHashMap.newKeySet();
        int numberOfThreads = 100;
        ThreadUtils.fireAtOnce(IntStream.range(0, numberOfThreads).mapToObj(i -> (Runnable) () -> {
                    ExpensiveObject instance = lazyInitRace.getInstance();
                    createdInstances.add(instance);
                }
        ).collect(Collectors.toList()));
        System.out.println("createdInstances.size() = " + createdInstances.size());
        Assertions.assertEquals(SINGLE == createdInstances.size(), isThreadSafe);
    }

    @Test
    void noLazyInstanceInitialized() {
        LazyInitHolderIdiom lazyInitRaceLazyHolderIdiom = new LazyInitHolderIdiom();
    }

    @Test
    void oneLazyInstanceInitialized() {
        LazyInitHolderIdiom lazyInitRaceLazyHolderIdiom = new LazyInitHolderIdiom();
        lazyInitRaceLazyHolderIdiom.getInstance();
    }
}