package pete.eremeykin.experiments;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

class InterviewQuestion {

    void printNumbers() {
        IntStream.range(1, 10)
                .mapToObj(i -> CompletableFuture.runAsync(
                        () -> System.out.println(i + " ")
                ))
                .forEach(CompletableFuture::join);
    }
}
