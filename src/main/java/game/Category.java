package game;

public enum Category {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private final String name;

    Category(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}