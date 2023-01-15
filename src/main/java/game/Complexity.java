package game;

public class Complexity {

    void movePlayer(Player player, int rolledNumber) {
        int fieldNumber;

        if (rolledNumber == 1) {
            return;
        }
        switch (rolledNumber) {
            case 3:
                fieldNumber = 5;
                break;
            case 5:
                fieldNumber = 10;
                break;
            default:
                fieldNumber = 2;
        }

        player.moveToField(fieldNumber);
    }
}
