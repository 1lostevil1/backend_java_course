package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {

    enum Type {
        CAT("CAT"),
        DOG("DOG"),
        BIRD("BIRD"),
        FISH("FISH"),
        SPIDER("SPIDER");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    enum Sex {
        M("M"),
        F("F");

        private String name;

        Sex(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public int paws() {
        final int FOUR = 4;
        final int EIGHT = 8;
        return switch (type) {
            case CAT, DOG -> FOUR;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> EIGHT;
        };
    }
}
