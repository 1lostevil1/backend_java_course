package edu.hw4;

import java.util.Comparator;

public class TypeComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        return o1.type().getName().compareTo(o2.type().getName());
    }
}
