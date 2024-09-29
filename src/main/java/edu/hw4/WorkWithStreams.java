package edu.hw4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkWithStreams {

    private WorkWithStreams() {

    }

    public static List<Animal> task1(Sample sample) {
        return sample.userList.stream()
            .sorted(Comparator.comparing(Animal::height))
            .toList();
    }

    public static List<Animal> task2(Sample sample, int k) {
        return k < 1 ? null
            : sample.userList.stream()
            .sorted(new WeightReverseOrderComparator())
            .limit(k)
            .toList();
    }

    public static Map<Animal.Type, Long> task3(Sample sample) {
        return sample.userList.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
    }

    public static Animal task4(Sample sample) {
        Optional<Animal> maxSizeOfName = sample.userList.stream()
            .max(Comparator.comparing(animal -> animal.name().length()));
        return maxSizeOfName.orElse(null);
    }

    public static Animal.Sex task5(Sample sample) {
        Map<Animal.Sex, Long> map = sample.userList.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));
        return map.get(Animal.Sex.M) > map.get(Animal.Sex.F) ? Animal.Sex.M
            : map.get(Animal.Sex.M) < map.get(Animal.Sex.F) ? Animal.Sex.F : null;
    }

    public static Map<Animal.Type, Animal> task6(Sample sample) {
        Map<Animal.Type, List<Animal>> map = sample.userList.stream()
            .collect(Collectors.groupingBy(Animal::type));
        Map<Animal.Type, Animal> newMap = new HashMap<>();
        for (var it : map.entrySet()) {
            Animal animal = map.get(it.getKey())
                .stream()
                .sorted(new WeightReverseOrderComparator())
                .toList()
                .get(0);
            newMap.put(it.getKey(), animal);
        }
        return newMap;
    }

    public static Animal task7(Sample sample, int k) {
        return (k < 1 || k > sample.userList.size()) ? null
            : sample.userList.stream()
            .sorted(Comparator.comparing(Animal::age).reversed())
            .limit(k)
            .toList().get(k - 1);
    }

    public static Optional<Animal> task8(Sample sample, int k) {
        return k < 0 ? Optional.empty()
            : sample.userList.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparing(Animal::weight));
    }

    public static Integer task9(Sample sample) {
        return (int) sample.userList.stream()
            .mapToInt(Animal::paws)
            .summaryStatistics()
            .getSum();
    }

    public static List<Animal> task10(Sample sample) {
        return sample.userList.stream()
            .filter(animal -> (animal.paws() != animal.age()))
            .toList();
    }

    public static List<Animal> task11(Sample sample) {
        final int HUNDRED = 100;
        return sample.userList.stream()
            .filter(Animal::bites)
            .filter(animal -> animal.height() > HUNDRED)
            .toList();
    }

    public static Integer task12(Sample sample) {
        return sample.userList.stream()
            .filter(animal -> animal.height() < animal.weight())
            .toList().size();
    }

    public static List<Animal> task13(Sample sample) {
        return sample.userList.stream()
            .filter(animal -> {
                boolean isTwoWordName = true;
                String s = animal.name();
                if (!s.contains(" ")) {
                    isTwoWordName = false;
                } else {
                    if (s.indexOf(" ") == s.length() - 1
                        || s.indexOf(" ") == 0
                        || Character.toString(s.charAt(s.indexOf(" ") + 1)).equals(" ")) {
                        isTwoWordName = false;
                    }
                }
                return isTwoWordName;
            })
            .toList();
    }

    public static Boolean task14(Sample sample, int k) {
        if (k >= 0) {
            List<Animal> listWithDog = sample.userList.stream()
                .filter(animal -> animal.type().equals(Animal.Type.DOG))
                .filter(animal -> animal.height() > k)
                .toList();
            return !listWithDog.isEmpty();
        }
        return null;
    }

    public static Map<Animal.Type, Integer> task15(Sample sample, int k, int l) {
        if (k < l && k >= 0) {
            Map<Animal.Type, List<Animal>> map = sample.userList.stream()
                .collect(Collectors.groupingBy(Animal::type));
            Map<Animal.Type, Integer> newMap = new HashMap<>();
            for (var it : map.entrySet()) {
                Integer sumOfWeight = (int) map.get(it.getKey())
                    .stream()
                    .filter(animal -> animal.age() >= k && animal.age() < l)
                    .mapToInt(Animal::weight)
                    .summaryStatistics()
                    .getSum();
                newMap.put(it.getKey(), sumOfWeight);
            }
            return newMap;
        }
        return null;
    }

    public static List<Animal> task16(Sample sample) {
        return sample.userList.stream()
            .sorted(new TypeComparator()
                .thenComparing(new SexComparator())
                .thenComparing(Animal::name))
            .toList();
    }

    public static Boolean task17(Sample sample) {
        Map<Animal.Type, List<Animal>> mapWithDogAndSpider = sample.userList.stream()
            .filter(animal -> animal.type().equals(Animal.Type.DOG) || animal.type().equals(Animal.Type.SPIDER))
            .collect(Collectors.groupingBy(Animal::type));
        int countBitesDog = mapWithDogAndSpider.get(Animal.Type.DOG)
            .stream()
            .filter(animal -> animal.bites())
            .toList()
            .size();
        int countBitesSpider = mapWithDogAndSpider.get(Animal.Type.SPIDER)
            .stream()
            .filter(animal -> animal.bites())
            .toList()
            .size();
        return countBitesSpider > countBitesDog;
    }

    public static Animal task18(Sample sample) {
        List<Animal> allAnimal = new ArrayList<>();
        for (var it : sample.megaUserList) {
            allAnimal.addAll(it);
        }
        return allAnimal.stream()
            .filter(animal -> animal.type().equals(Animal.Type.FISH))
            .sorted(new WeightReverseOrderComparator())
            .toList()
            .get(0);
    }

    public static Map<String, Set<ValidationError>> task19(Sample sample) {
        Map<String, Set<ValidationError>> mapOfErrorForEachAnimal = new HashMap<>();
        sample.brokenList.stream()
            .forEach((animal) -> {
                if (!(new ValidationError().check(animal).isEmpty())) {
                    mapOfErrorForEachAnimal.put(animal.name(), new ValidationError().check(animal));
                }
            });
        return mapOfErrorForEachAnimal;
    }

    public static Map<String, String> task20(Sample sample) {
        Map<String, String> mapOfErrorForEachAnimal = new HashMap<>();
        sample.brokenList.stream()
            .forEach((animal) -> {
                if (!(new ValidationError().checkUpdate(animal).isEmpty())) {
                    mapOfErrorForEachAnimal.put(animal.name(), new ValidationError().checkUpdate(animal));
                }
            });
        return mapOfErrorForEachAnimal;
    }
}
