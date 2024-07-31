package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

import java.util.HashSet;
import java.util.Set;

@Listing("3.11")
final class ThreeStooges {
    private final Set<String> stooges = new HashSet<>();

    public ThreeStooges(){
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }

}
