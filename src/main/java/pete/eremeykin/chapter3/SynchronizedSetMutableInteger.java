package pete.eremeykin.chapter3;

class SynchronizedSetMutableInteger implements MutableInteger {
    private int value;

    @Override
    public int get() {
        return value;
    }

    @Override
    public synchronized void set(int value) {
        this.value = value;
    }
}
