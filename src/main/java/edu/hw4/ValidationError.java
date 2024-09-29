package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public class ValidationError {

    private String errorMessage;

    protected ValidationError() {

    }

    private ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Set<ValidationError> check(Animal animal) {
        Set<ValidationError> setOfErrorForAnimal = new HashSet<>();
        if (animal.name() == null) {
            setOfErrorForAnimal.add(new ValidationError("Name is null"));
        } else if (animal.name().isEmpty()) {
            setOfErrorForAnimal.add(new ValidationError("Name is empty"));
        }
        if (animal.type() == null) {
            setOfErrorForAnimal.add(new ValidationError("Type is null"));
        }
        if (animal.sex() == null) {
            setOfErrorForAnimal.add(new ValidationError("Sex is null"));
        }
        if (animal.age() < 0) {
            setOfErrorForAnimal.add(new ValidationError("Age is a negative number"));
        }
        if (animal.height() < 0) {
            setOfErrorForAnimal.add(new ValidationError("Height is a negative number"));
        }
        if (animal.weight() < 0) {
            setOfErrorForAnimal.add(new ValidationError("Weight is a negative number"));
        }
        return setOfErrorForAnimal;
    }

    public String checkUpdate(Animal animal) {
        String s = "";
        if (animal.name() == null) {
            s += "name : Name is null";
        } else if (animal.name().isEmpty()) {
            s += s.isEmpty() ? "" : ", ";
            s += "name : Name is empty";
        }
        if (animal.type() == null) {
            s += s.isEmpty() ? "" : ", ";
            s += "type : Type is null";
        }
        if (animal.sex() == null) {
            s += s.isEmpty() ? "" : ", ";
            s += "sex : Sex is null";
        }
        if (animal.age() < 0) {
            s += s.isEmpty() ? "" : ", ";
            s += "age : Age is a negative number";
        }
        if (animal.height() < 0) {
            s += s.isEmpty() ? "" : ", ";
            s += "height : Height is a negative number";
        }
        if (animal.weight() < 0) {
            s += s.isEmpty() ? "" : ", ";
            s += "weight : Weight is a negative number";
        }
        return s;
    }
}
