package game;

public class Player {

    private final String name;
    private int purse;
    private boolean inPenaltyBox;
    private boolean isGettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.purse = 0;
        this.inPenaltyBox = false;
        this.isGettingOutOfPenaltyBox = false;
    }

    public void addCoin() {
        this.purse += 1;
        System.out.println(this.name
                + " now has "
                + this.purse
                + " Gold Coins.");
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

    public int getCoins() {
        return this.purse;
    }
}
