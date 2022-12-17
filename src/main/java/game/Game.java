package game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    List<Player> players = new ArrayList<>();
    QuestionStack questionStack;

    int indexOfCurrentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        this.questionStack = new QuestionStack();
    }

    public boolean addPlayer(String playerName) {
        this.players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int rolledNumber) {
        System.out.println(getCurrentPlayer().getName() + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (getCurrentPlayer().isInPenaltyBox()) {
            if (rolledNumber % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(getCurrentPlayer().getName() + " is getting out of the penalty box");
                getCurrentPlayer().setPlace(rolledNumber);
                System.out.println("The category is " + Category.getCategory(getCurrentPlayer().getPlace()).getCategoryName());
                askQuestion(getCurrentPlayer().getPlace());
            } else {
                System.out.println(getCurrentPlayer().getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            getCurrentPlayer().setPlace(rolledNumber);
            System.out.println("The category is " + Category.getCategory(getCurrentPlayer().getPlace()).getCategoryName());
            askQuestion(getCurrentPlayer().getPlace());
        }
    }

    private Player getCurrentPlayer() {
        return this.players.get(this.indexOfCurrentPlayer);
    }

    private void askQuestion(int place) {
        Category category = Category.getCategory(place);
        Question askedQuestion = this.questionStack.removeQuestion(category);
        System.out.println(askedQuestion);
    }

    public boolean wasCorrectlyAnswered() {
        if (getCurrentPlayer().isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                getCurrentPlayer().addCoin();
                boolean winner = getCurrentPlayer().didPlayerWin();
                setNewCurrentPlayer();
                return winner;
            } else {
                setNewCurrentPlayer();
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            getCurrentPlayer().addCoin();
            boolean winner = getCurrentPlayer().didPlayerWin();
            setNewCurrentPlayer();
            return winner;
        }
    }

    public boolean wasIncorrectlyAnswered() {
        System.out.println("Question was incorrectly answered");
        System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");
        getCurrentPlayer().setInPenaltyBox(true);
        setNewCurrentPlayer();
        return true;
    }

    private void setNewCurrentPlayer() {
        int firstPlacing = 0;
        this.indexOfCurrentPlayer++;
        if (this.indexOfCurrentPlayer == players.size()) this.indexOfCurrentPlayer = firstPlacing;
    }
}

