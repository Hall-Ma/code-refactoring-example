package game;

public class Player {

    private final String name;
    private boolean isGettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.isGettingOutOfPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public boolean isAllowedToAnswer() {
        return isGettingOutOfPenaltyBox;
    }

    public void setIsAllowedToAnswer(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }
}
