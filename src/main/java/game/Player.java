package game;

public class Player {

    private final String name;
    private boolean isAllowedToAnswer;

    public Player(String name) {
        this.name = name;
        this.isAllowedToAnswer = false;
    }

    public boolean isAllowedToAnswer() {
        return isAllowedToAnswer;
    }

    public void setIsAllowedToAnswer(boolean isAllowedToAnswer) {
        this.isAllowedToAnswer = isAllowedToAnswer;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
