package pete.eremeykin.chapter4;

import pete.eremeykin.Listing;

@Listing("4.3")
class PrivateLock {
    private final Object myLock = new Object();
    Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // Access or modify the state of widget
        }
    }


    static class Widget {

    }
}
