package game;

public class Player {

    private final String name;
    private boolean inPenaltyBox;
    private boolean isGettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.inPenaltyBox = false;
        this.isGettingOutOfPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public boolean isAllowedToAnswer() {
        return isGettingOutOfPenaltyBox;
    }

    public void setIsAllowedToAnswer(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }
}
