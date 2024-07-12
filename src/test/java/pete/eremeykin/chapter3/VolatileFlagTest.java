package pete.eremeykin.chapter3;

import org.junit.jupiter.api.Test;
import pete.eremeykin.utils.ThreadUtils;

class VolatileFlagTest {


    @Test
    void shouldCountSheep() {
        VolatileFlag volatileFlag = new VolatileFlag();
        Thread sheepCountingThread = new Thread(volatileFlag);
        sheepCountingThread.start();
        ThreadUtils.withNoInterruption(() -> {
                    Thread.sleep(5000L);
                    volatileFlag.setAsleep(true);
                    sheepCountingThread.join();
                }
        );
    }
}