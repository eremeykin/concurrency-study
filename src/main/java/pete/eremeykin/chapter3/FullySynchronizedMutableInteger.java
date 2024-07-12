package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

@Listing("3.3")
class FullySynchronizedMutableInteger implements MutableInteger {
    private int value;

    @Override
    public synchronized int get() {
        return value;
    }

    @Override
    public synchronized void set(int value) {
        this.value = value;
    }
}
