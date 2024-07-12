package pete.eremeykin.chapter3;

import org.junit.jupiter.api.Test;
import pete.eremeykin.chapter3.NoVisibility.ReaderThread;

import java.util.ArrayList;
import java.util.List;

class NoVisibilityTest {
    public static final int NUMBER_OF_ITERATIONS = 10_000;

    // Hard to capture it with a test
    // It should be possible to see number = 0 sometimes
    @Test
    void shouldBehaveStrangely() throws InterruptedException {
        List<Thread> threadsToJoin = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            NoVisibility noVisibility = new NoVisibility();
            ReaderThread readerThread = noVisibility.new ReaderThread();
            threadsToJoin.add(readerThread);
            readerThread.start();
            noVisibility.number = 42;
            noVisibility.ready = true;
        }
        for (Thread thread : threadsToJoin) {
            thread.join();
        }
    }
}