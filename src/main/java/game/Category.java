package game;

import java.util.Arrays;
import java.util.List;


public enum Category {
    POP("Pop", Arrays.asList(0, 4, 8)),
    SCIENCE("Science", Arrays.asList(1, 5, 9)),
    SPORTS("Sports", Arrays.asList(2, 6, 10)),
    ROCK("Rock", Arrays.asList(3, 7, 11));

    private final String name;
    private final List<Integer> ints;

    Category(final String name, List<Integer> ints) {
        this.name = name;
        this.ints = ints;
    }

    public String getCategoryName() {
        return name;
    }

    public List<Integer> getInts() {
        return ints;
    }
}