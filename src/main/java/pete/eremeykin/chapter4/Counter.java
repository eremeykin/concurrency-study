package pete.eremeykin.chapter4;

import pete.eremeykin.Listing;
import pete.eremeykin.ThreadSafe;

@Listing("4.1")
@ThreadSafe
final class Counter {
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    private synchronized void increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("Counter overflow");
        }
        ++value;
    }
}
