package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

@Listing("3.6")
class UnsafeStates {
    private String[] states = new String[]{
            "AK", "AL" // ... etc.
    };

    public String[] getStates() {
        return states;
    }
}
