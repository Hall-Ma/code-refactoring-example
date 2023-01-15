package game;

public class Complexity2 {

    void movePlayer(Player player, int rolledNumber) {
        int fieldNumber = 5;

        if (rolledNumber == 1 && player.isInPenaltyBox()) {
            fieldNumber = 0;
        }
        player.moveToField(fieldNumber);
    }
}
