package edu.hw7.Task3;

import org.jetbrains.annotations.Nullable;

interface PersonDatabase {

    void add(Person person);

    void delete(int id);

    @Nullable Person findByName(String name);

    @Nullable Person findByAddress(String address);

    @Nullable Person findByPhone(String phone);
}
