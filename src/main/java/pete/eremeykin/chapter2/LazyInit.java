package pete.eremeykin.chapter2;

import pete.eremeykin.FromTheInternet;
import pete.eremeykin.Listing;
import pete.eremeykin.NotThreadSafe;
import pete.eremeykin.ThreadSafe;

interface LazyInit {

    ExpensiveObject getInstance();

    class ExpensiveObject {
        public ExpensiveObject() {
            System.out.println("Lazy instance initialized!");
        }
    }

    @Listing("2.3")
    @NotThreadSafe
    class LazyInitRace implements LazyInit {

        LazyInitRace() {
        }

        private ExpensiveObject instance = null;

        public /*synchronized*/ ExpensiveObject getInstance() {
            if (instance == null) {
                instance = new ExpensiveObject();
            }
            return instance;
        }
    }

    @FromTheInternet("https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom")
    @ThreadSafe
    class LazyInitHolderIdiom implements LazyInit {
        public LazyInitHolderIdiom() {}

        private static class LazyHolder {
            private static final ExpensiveObject INSTANCE = new ExpensiveObject();
        }

        public ExpensiveObject getInstance() {
            return LazyHolder.INSTANCE;
        }
    }
}

