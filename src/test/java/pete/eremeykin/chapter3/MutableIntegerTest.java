package pete.eremeykin.chapter3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pete.eremeykin.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pete.eremeykin.chapter3.MutableIntegerTest.EventRecord.Type.READ;
import static pete.eremeykin.chapter3.MutableIntegerTest.EventRecord.Type.WRITE;

// not quite successful, but the best what I was able to achieve
class MutableIntegerTest {
    private static final int NUMBER_OF_THREADS = 2;
    private static final int NUMBER_OF_ITERATIONS_IN_THREAD = 100_000;


    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of(new SimpleMutableInteger(), false),
                Arguments.of(new SynchronizedSetMutableInteger(), false),
                Arguments.of(new FullySynchronizedMutableInteger(), true)
        );
    }

    static class EventRecord {
        private final Type type;
        private final int value;

        public EventRecord(Type type, int value) {
            this.type = type;
            this.value = value;
        }

        enum Type {
            READ, WRITE
        }

        @Override
        public String toString() {
            return type + " = " + value;
        }
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void shouldFailIfNotFullySynchronized(MutableInteger mutableInteger, boolean isFullySynchronized) {
        List<EventRecord> events = Collections.synchronizedList(new ArrayList<>());
        List<Runnable> threads = IntStream.range(0, NUMBER_OF_THREADS).mapToObj((i) -> (Runnable) () -> {
                    boolean isReader = i % 2 == 0;
                    for (int j = 0; j < NUMBER_OF_ITERATIONS_IN_THREAD; j++) {
                        if (isReader) {
                            int value = mutableInteger.get();
                            events.add(new EventRecord(READ, value));
                        } else {
                            mutableInteger.set(j);
                            int value = mutableInteger.get();
                            events.add(new EventRecord(WRITE, value));
                        }
                    }
                }
        ).toList();
        ThreadUtils.fireAtOnce(threads);
        List<StaleRead> staleReads = findStaleReads(events);
        System.out.println("events size = " + events.size());
        System.out.println("stale reads = " + staleReads.size());
        System.out.println(staleReads);
        System.out.println();
//        prettyPrintEvents(events);
    }

    private static void prettyPrintEvents(List<EventRecord> events) {
        String tab = "        ";
        String header = (WRITE + "         ").substring(0, tab.length()) + READ;
        System.out.println(header);
        for (EventRecord event : events) {
            if (event.type == WRITE) {
                System.out.println(event.value);
            } else {
                System.out.println(tab + event.value);
            }
        }
    }

    private static class StaleRead {
        private final EventRecord write;
        private final EventRecord read;

        public StaleRead(EventRecord write, EventRecord read) {
            this.write = write;
            this.read = read;
        }

        @Override
        public String toString() {
            return write + " " + read;
        }
    }

    private List<StaleRead> findStaleReads(List<EventRecord> events) {
        EventRecord previousWrite = null;
        List<StaleRead> result = new ArrayList<>();
        for (EventRecord event : events) {
            switch (event.type) {
                case WRITE -> previousWrite = event;
                case READ -> {
                    // previousWrite.value = event.value
                    //                         - OK, normal read
                    // previousWrite.value < event.value
                    //                         - OK, writer reports event after actual write
                    // previousWrite.value > event.value
                    //                         - NOT OK, stale read
                    //                           (actually, not only: there can be a case when the reader fetched a value
                    //                            but hasn't added it into events yet, while the writer has.)
                    if (previousWrite != null && previousWrite.value > event.value) {
                        result.add(new StaleRead(previousWrite, event));
                    }
                }
            }
        }
        return result;
    }
}