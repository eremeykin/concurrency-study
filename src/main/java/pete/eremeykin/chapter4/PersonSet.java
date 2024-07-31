package pete.eremeykin.chapter4;

import pete.eremeykin.Listing;
import pete.eremeykin.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@Listing("4.2")
@ThreadSafe
class PersonSet {
    private final Set<Person> mySet = new HashSet<>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person person) {
        return mySet.contains(person);
    }

    static class Person {

    }

}
