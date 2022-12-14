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

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        QuestionGenerator questionGenerator = new QuestionGenerator();
        this.popQuestions = questionGenerator.getPopQuestions();
        this.scienceQuestions = questionGenerator.getScienceQuestions();
        this.sportsQuestions = questionGenerator.getSportsQuestions();
        this.rockQuestions = questionGenerator.getRockQuestions();
    }

    public boolean add(String playerName) {
        this.players.add(new Player(playerName));

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(getPlayer().getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (getPlayer().isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(getPlayer().getName() + " is getting out of the penalty box");
                setPlayersPlace(roll);
                askQuestion();
            } else {
                System.out.println(getPlayer().getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            setPlayersPlace(roll);
            askQuestion();
        }

    }

    private void setPlayersPlace(int roll) {
        int numberOutOfPlaces = 11;
        int maxNumberOfPlaces = 12;
        int newPlace;
        newPlace = getPlayer().getPlace() + roll;
        getPlayer().setPlace(newPlace);
        if (getPlayer().getPlace() > numberOutOfPlaces)
            newPlace -= maxNumberOfPlaces;
        getPlayer().setPlace(newPlace);

        System.out.println(getPlayer().getName()
                + "'s new location is "
                + getPlayer().getPlace());
        System.out.println("The category is " + currentCategory().getCategoryName());
    }

    private Player getPlayer() {
        return this.players.get(this.currentPlayer);
    }

    private void askQuestion() {
        if (currentCategory() == Category.POP)
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == Category.SCIENCE)
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == Category.SPORTS)
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == Category.ROCK)
            System.out.println(rockQuestions.removeFirst());
    }


    private Category currentCategory() {
        if (Category.POP.getNumberOfCategory().contains(getPlayer().getPlace()))
            return Category.POP;
        if (Category.SCIENCE.getNumberOfCategory().contains(getPlayer().getPlace()))
            return Category.SCIENCE;
        if (Category.SPORTS.getNumberOfCategory().contains(getPlayer().getPlace()))
            return Category.SPORTS;
        return Category.ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (getPlayer().isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
            setCurrentPlayersPlace();
            return true;
        }
        System.out.println("Answer was correct!!!!");
        getPlayer().addCoin();
        setCurrentPlayersPlace();
        return didPlayerWin();
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(getPlayer().getName() + " was sent to the penalty box");
        getPlayer().setInPenaltyBox(true);

        setCurrentPlayersPlace();
        return true;
    }

    private void setCurrentPlayersPlace() {
        int firstPlacing = 0;
        this.currentPlayer++;
        if (this.currentPlayer == players.size()) this.currentPlayer = firstPlacing;
    }


    private boolean didPlayerWin() {
        int numberOfCoinsToWin = 6;
        return !(getPlayer().getPurse() == numberOfCoinsToWin);
    }
}
