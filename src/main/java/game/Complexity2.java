package game;

public class Complexity2 {

    void movePlayer(Player player, int rolledNumber) {
        int fieldNumber = 2;

        if (rolledNumber == 1) {
            if (player.isInPenaltyBox()) {
                fieldNumber = 5;
            }
            player.moveToField(fieldNumber);
        }

    }
}
