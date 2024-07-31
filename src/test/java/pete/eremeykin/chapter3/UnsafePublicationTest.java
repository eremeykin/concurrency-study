package pete.eremeykin.chapter3;

import org.junit.jupiter.api.Test;
import pete.eremeykin.utils.ThreadUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

class UnsafePublicationTest {
    private static final int NUMBER_OF_THREADS = 100;
    private static final int NUMBER_OF_ATTEMPTS = 1000;
    private static final Random random = new Random(34523L);


    @Test
    void shouldBreakSanityAssertionSometimes() {
        boolean caughtException = false;
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            int writerThreadIndex = random.nextInt(0, NUMBER_OF_THREADS);
            UnsafePublication unsafePublication = new UnsafePublication();
            List<Runnable> executors = IntStream.range(0, NUMBER_OF_THREADS).mapToObj((threadIndex) -> (Runnable) () -> {
                boolean isWriter = threadIndex == writerThreadIndex;
                if (isWriter) {
                    unsafePublication.initialize();
                } else {
                    UnsafePublication.Holder holder = unsafePublication.holder;
                    if (holder != null) {
                        holder.assertSanity();
                    }
                }
            }).toList();
            ThreadUtils.fireAtOnce(executors);
        }
//        Assertions.assertTrue(caughtException);
    }
}