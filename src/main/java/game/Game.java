package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    List<Player> players = new ArrayList<>();
    LinkedList<Category> popQuestions;
    LinkedList<Category> scienceQuestions;
    LinkedList<Category> sportsQuestions;
    LinkedList<Category> rockQuestions;

    int indexOfCurrentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        QuestionGenerator questionGenerator = new QuestionGenerator();
        this.popQuestions = questionGenerator.getPopQuestions();
        this.scienceQuestions = questionGenerator.getScienceQuestions();
        this.sportsQuestions = questionGenerator.getSportsQuestions();
        this.rockQuestions = questionGenerator.getRockQuestions();
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

    public void roll(int roll) {
        System.out.println(getCurrentPlayer().getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (getCurrentPlayer().isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(getCurrentPlayer().getName() + " is getting out of the penalty box");
                getCurrentPlayer().setPlace(roll);
                System.out.println("The category is " + Category.getCategory(getCurrentPlayer().getPlace()).getCategoryName());
                askQuestion(getCurrentPlayer().getPlace());
            } else {
                System.out.println(getCurrentPlayer().getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            getCurrentPlayer().setPlace(roll);
            System.out.println("The category is " + Category.getCategory(getCurrentPlayer().getPlace()).getCategoryName());
            askQuestion(getCurrentPlayer().getPlace());
        }

    }

    private Player getCurrentPlayer() {
        return this.players.get(this.indexOfCurrentPlayer);
    }

    private void askQuestion(int place) {
        if (Category.getCategory(place) == Category.POP)
            System.out.println(popQuestions.removeFirst());
        if (Category.getCategory(place) == Category.SCIENCE)
            System.out.println(scienceQuestions.removeFirst());
        if (Category.getCategory(place) == Category.SPORTS)
            System.out.println(sportsQuestions.removeFirst());
        if (Category.getCategory(place) == Category.ROCK)
            System.out.println(rockQuestions.removeFirst());
    }


    public boolean wasCorrectlyAnswered() {
        if (getCurrentPlayer().isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
            setNewCurrentPlayer();
            return true;
        }
        System.out.println("Answer was correct!!!!");
        getCurrentPlayer().addCoin();
        setNewCurrentPlayer();
        return getCurrentPlayer().didPlayerWin();
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

