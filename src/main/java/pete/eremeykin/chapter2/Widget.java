package pete.eremeykin.chapter2;

import pete.eremeykin.Listing;

@Listing("2.7")
class Widget {
    public synchronized void doSomething() {

    }

    static class LoggingWidget extends Widget {
        @Override // synchronized requires this lock
        public synchronized void doSomething() {
            System.out.println(toString() + ": calling doSomething()");
            // super.doSomething() requires this lock - OK as it is reentrant
            super.doSomething();
        }
    }
}
