package edu.hw4;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("uncommentedmain")
public class Sample {
    protected final List<List<Animal>> megaUserList;
    protected final List<Animal> userList = Arrays.asList(
        new Animal("MrБарсик", Animal.Type.CAT, Animal.Sex.M, 10, 20, 5, true),
        new Animal("Кругль", Animal.Type.DOG, Animal.Sex.M, 7, 40, 10, true),
        new Animal("Чижик ", Animal.Type.BIRD, Animal.Sex.F, 2, 10, 1, false),
        new Animal("Капрыч", Animal.Type.FISH, Animal.Sex.F, 3, 5, 1, false),
        new Animal("Гена", Animal.Type.SPIDER, Animal.Sex.M, 77, 20, 2, true),
        new Animal("Саня", Animal.Type.CAT, Animal.Sex.M, 18, 170, 60, true),
        new Animal("Барон", Animal.Type.DOG, Animal.Sex.M, 10, 50, 6, true),
        new Animal("Питер", Animal.Type.SPIDER, Animal.Sex.F, 7, 20, 3, true)

    );

    protected final List<Animal> userList2 = Arrays.asList(
        new Animal("Кентик", Animal.Type.CAT, Animal.Sex.F, 101, 23, 3, true),
        new Animal("Крутыш", Animal.Type.DOG, Animal.Sex.F, 73, 30, 16, true),
        new Animal("Бубль", Animal.Type.BIRD, Animal.Sex.M, 22, 50, 5, false),
        new Animal("Глупи", Animal.Type.FISH, Animal.Sex.M, 5, 25, 2, false),
        new Animal("Генадий", Animal.Type.SPIDER, Animal.Sex.F, 3, 10, 3, true),
        new Animal("Мурзик", Animal.Type.CAT, Animal.Sex.F, 1, 10, 67, true),
        new Animal("Кира", Animal.Type.DOG, Animal.Sex.F, 15, 40, 9, true)
    );

    protected final List<Animal> brokenList = Arrays.asList(
        new Animal("", Animal.Type.CAT, Animal.Sex.F, 101, 23, 3, true),
        new Animal("Собака", Animal.Type.DOG, Animal.Sex.F, 73, 30, 16, true),
        new Animal("Бубликс", null, Animal.Sex.M, -22, 50, 5, false),
        new Animal("Рыба", Animal.Type.FISH, Animal.Sex.F, 5, 25, 2, false),
        new Animal("Лапыч", Animal.Type.SPIDER, Animal.Sex.F, 10, 10, 3, true),
        new Animal("Сашкент", Animal.Type.CAT, Animal.Sex.F, 1, 10, 67, true),
        new Animal("Медведь", Animal.Type.DOG, Animal.Sex.F, 15, 40, 9, true)
    );

    protected final List<Animal> nullList = List.of();

    protected Sample() {
        megaUserList = Arrays.asList(userList, userList2);
    }

    public static void main(String[] args) {
        final int THREE = 3;
        final int HUNDRED = 100;
        final int FOURTY_FIVE = 45;
        final int FIVE = 5;
        final int TEN = 10;
        Sample sample = new Sample();
        WorkWithStreams.task1(sample);
        WorkWithStreams.task2(sample, THREE);
        WorkWithStreams.task3(sample);
        WorkWithStreams.task4(sample);
        WorkWithStreams.task5(sample);
        WorkWithStreams.task6(sample);
        WorkWithStreams.task7(sample, 1);
        WorkWithStreams.task8(sample, HUNDRED);
        WorkWithStreams.task9(sample);
        WorkWithStreams.task10(sample);
        WorkWithStreams.task11(sample);
        WorkWithStreams.task12(sample);
        WorkWithStreams.task13(sample);
        WorkWithStreams.task14(sample, FOURTY_FIVE);
        WorkWithStreams.task15(sample, FIVE, TEN);
        WorkWithStreams.task16(sample);
        WorkWithStreams.task17(sample);
        WorkWithStreams.task18(sample);
        WorkWithStreams.task19(sample);
        WorkWithStreams.task20(sample);
    }
}
