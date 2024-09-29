package edu.hw4;

import java.util.Comparator;

public class SexComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal o1, Animal o2) {
        return o1.sex().getName().compareTo(o2.sex().getName());
    }
}
