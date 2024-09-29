package edu.hw4;

import java.util.Comparator;

public class WeightReverseOrderComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        return o2.weight() - o1.weight();
    }
}
