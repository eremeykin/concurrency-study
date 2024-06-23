package pete.eremeykin;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ThreadUtils {
    private ThreadUtils() {
    }

    public static void fireAtOnce(List<Runnable> runnableList) {
        var numberOfThreads = runnableList.size();
        var latch = new CountDownLatch(numberOfThreads);
        var threads = runnableList.stream().map(runnable -> new Thread(() -> {
            latch.countDown();
            runnable.run();
        })).toList();
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
    }

}
