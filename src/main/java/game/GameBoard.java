package game;

import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public class GameBoard {

    private final Map<Integer, Category> categoryByField = ofEntries(
            entry(0, Category.POP),
            entry(1, Category.SCIENCE),
            entry(2, Category.SPORTS),
            entry(3, Category.ROCK),
            entry(4, Category.POP),
            entry(5, Category.SCIENCE),
            entry(6, Category.SPORTS),
            entry(7, Category.ROCK),
            entry(8, Category.POP),
            entry(9, Category.SCIENCE),
            entry(10, Category.SPORTS),
            entry(11, Category.ROCK)
    );

    public Category getCategoryByGameField(int gameField) {
        return this.categoryByField.get(gameField);
    }
}
