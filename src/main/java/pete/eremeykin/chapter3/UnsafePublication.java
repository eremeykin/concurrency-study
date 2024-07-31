package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

@Listing("3.14")
class UnsafePublication {
    public Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }

    @Listing("3.15")
    static class Holder {
        public int n;

        public Holder(int n) {
            this.n = n;
        }

        public void assertSanity() {
            if (n != n) {
                throw new AssertionError("This statement is false");
            }
        }
    }
}
