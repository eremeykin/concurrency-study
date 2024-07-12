package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

@Listing("3.2")
class SimpleMutableInteger implements MutableInteger {
    private int value;

    @Override
    public int get() {
        return value;
    }

    @Override
    public void set(int value) {
        this.value = value;
    }
}
