package game;

import java.util.HashMap;
import java.util.Map;

public class Treasurer {
    private final int START_COINS = 0;
    private final int COINS_PER_ROUND = 1;
    private final int COINS_NEEDED_TO_WIN = 6;
    private final Map<Player, Integer> purse = new HashMap<>();

    public void setInitialCoinsForPlayer(Player player) {
        this.purse.put(player, START_COINS);
    }

    public void addCoin(Player player) {
        int increasedCoins = this.purse.get(player) + COINS_PER_ROUND;
        this.purse.put(player, increasedCoins);
        System.out.println(player + " now has " + increasedCoins + " Gold Coins.");
    }

    public boolean didPlayersReachedMaxCoins() {
        return this.purse.values().stream().allMatch(coins -> coins != COINS_NEEDED_TO_WIN);
    }
}
