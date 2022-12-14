package game;

public class Player {

    private final String name;
    private int place;
    private int purse;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.place = 0;
        this.purse = 0;
        this.inPenaltyBox = false;
    }

    public void addCoin() {
        this.purse += 1;
        System.out.println(this.name
                + " now has "
                + this.purse
                + " Gold Coins.");
    }

    public void setPlace(int roll) {
        int maxNumberOfPlaces = 12;
        int newPlace = this.place + roll;

        if (newPlace >= maxNumberOfPlaces) {
            newPlace -= maxNumberOfPlaces;
        }
        this.place = newPlace;
        System.out.println(this.name
                + "'s new location is "
                + this.place);
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }



    public int getPurse() {
        return purse;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }
}
