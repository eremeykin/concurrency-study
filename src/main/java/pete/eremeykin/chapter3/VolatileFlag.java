package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

@Listing("3.4")
class VolatileFlag implements Runnable {
    private volatile boolean asleep;
    private int sheepNumber = 0;

    @Override
    public void run() {
        while (!asleep) {
            try {
                countSomeSheep();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void countSomeSheep() throws InterruptedException {
        Thread.sleep(500L);
        System.out.println(++sheepNumber);
    }

    public void setAsleep(boolean asleep) {
        this.asleep = asleep;
    }
}
