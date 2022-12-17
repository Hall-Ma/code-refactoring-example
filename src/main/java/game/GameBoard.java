package game;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public class GameBoard {
    private final int INITIAL_FIELD_TO_START = 0;
    private final Map<Player, Integer> gameFieldByPlayer = new HashMap();
    private final Map<Integer, Category> gameFieldByCategory = ofEntries(
            entry(INITIAL_FIELD_TO_START, Category.POP),
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
    private final int NUMBER_OF_GAME_FIELDS = this.gameFieldByCategory.size();

    public Category getCategoryByGameField(int gameField) {
        return this.gameFieldByCategory.get(gameField);
    }

    public void setInitialGameFieldForPlayer(Player player) {
        this.gameFieldByPlayer.put(player, INITIAL_FIELD_TO_START);
    }

    public int getGameFieldForPlayer(Player player) {
        return this.gameFieldByPlayer.get(player);
    }

    public void setGameFieldForPlayer(Player player, int rolledNumber) {
        int currentField = getGameFieldForPlayer(player);
        int newField = currentField + rolledNumber;
        if (newField >= NUMBER_OF_GAME_FIELDS) {
            newField -= NUMBER_OF_GAME_FIELDS;
        }
        this.gameFieldByPlayer.put(player, newField);
        System.out.println(player.getName()
                + "'s new location is "
                + newField);
    }

    public int getNumberOfPlayers() {
        return this.gameFieldByPlayer.size();
    }
}
