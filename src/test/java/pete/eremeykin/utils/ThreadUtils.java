package pete.eremeykin.utils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ThreadUtils {
    private ThreadUtils() {
    }

    public static <R extends Runnable> void fireAtOnce(List<R> runnableList) {
        var numberOfThreads = runnableList.size();
        CountDownLatch readyThreadLatch = new CountDownLatch(numberOfThreads);
        CountDownLatch callingThreadLatch = new CountDownLatch(1);
        var threads = runnableList.stream().map(runnable -> new Thread(() -> withNoInterruption(() -> {
            readyThreadLatch.countDown();
            callingThreadLatch.await();
            runnable.run();
        }))).toList();
        threads.forEach(Thread::start);
        withNoInterruption(() -> {
//            readyThreadLatch.await();
            callingThreadLatch.countDown();
            for (Thread t : threads) {
                t.join();
            }
        });
    }


    public static void sleep(long millis) {
        withNoInterruption(() -> Thread.sleep(millis));
    }

    @FunctionalInterface
    public interface Callback {
        void run() throws InterruptedException;
    }

    public static void withNoInterruption(Callback callback) {
        try {
            callback.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
