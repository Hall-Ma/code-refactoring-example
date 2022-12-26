package game;

import java.util.Random;

public class Dice {

    private final Random random;
    private final int MAX_NUMBER_TO_ROLL = 5;

    public Dice(Random random) {
        this.random = random;
    }

    public int roll() {
        return random.nextInt(MAX_NUMBER_TO_ROLL) + 1;
    }
}
