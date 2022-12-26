package game;

import java.util.ArrayList;
import java.util.List;

public class PenaltyBox {

    private final List<Player> playersInPenaltyBox = new ArrayList<>();

    public void sendPlayerIntoPenaltyBox(Player player) {
        this.playersInPenaltyBox.add(player);
    }

    public boolean isPlayerInPenaltyBox(Player player) {
        return this.playersInPenaltyBox.contains(player);
    }
}
