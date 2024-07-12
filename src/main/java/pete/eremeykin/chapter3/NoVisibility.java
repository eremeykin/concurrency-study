package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

@Listing("3.1")
class NoVisibility {
    boolean ready;
    int number;

    class ReaderThread extends Thread {

        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }
}
