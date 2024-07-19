package pete.eremeykin.chapter3;

import pete.eremeykin.Listing;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

@Listing("3.9")
class AnimalsEscape {
    private final Ark ark = new Ark();

    public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;

        // animals are confined to method, don't let them escape!
        animals = new TreeSet<>(new SpeciesGenderComparator());
        animals.addAll(candidates);
        for (Animal animal : animals) {
            if (candidate == null || !candidate.isPotentialMate(animal)) {
                candidate = animal;
            } else {
                ark.load(new AnimalPair(candidate, animal));
                ++numPairs;
                candidate = null;
            }
        }
        return numPairs;
    }

    static class AnimalPair {
        private final Animal firstAnimal;
        private final Animal secondAnimal;

        public AnimalPair(Animal firstAnimal, Animal secondAnimal) {
            this.firstAnimal = firstAnimal;
            this.secondAnimal = secondAnimal;
        }

        public Animal getFirstAnimal() {
            return firstAnimal;
        }

        public Animal getSecondAnimal() {
            return secondAnimal;
        }
    }


    static class Ark {

        public void load(AnimalPair animalPair) {

        }
    }

    static class SpeciesGenderComparator implements Comparator<Animal> {

        @Override
        public int compare(Animal o1, Animal o2) {
            return 0;
        }
    }

    static class Animal {
        private final String name;

        public Animal(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isPotentialMate(Animal animal) {
            return true;
        }
    }


}
