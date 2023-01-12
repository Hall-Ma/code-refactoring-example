package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.IntStream.range;

enum QuestionCategory {
    POP(0), SCIENCE(1), SPORTS(2), ROCK(3);

    private final int place;

    QuestionCategory(int place) {
        this.place = place;
    }

    public int getPlace() {
        return place;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}

class Player {
    private final String name;
    private int place;
    private int coins;
    private boolean isInPenaltyBox;

    Player(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    int getPlace() {
        return place;
    }

    void move(int roll) {
        place += roll;
        if (place >= 12) {
            place -= 12;
        }
    }

    void addCoin() {
        coins++;
    }

    int getCoins() {
        return coins;
    }

    void putInPenaltyBox() {
        isInPenaltyBox = true;
    }

    boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    void printNewLocation() {
        System.out.println(getName() + "'s new location is " + getPlace());
    }

    void printCoins() {
        System.out.println(getName()
                + " now has "
                + getCoins()
                + " Gold Coins.");
    }
}

interface IGame {
    void add(String chet);

    void roll(int i);

    boolean wrongAnswer();

    boolean wasCorrectlyAnswered();
}

public class Game implements IGame {
    private final List<Player> players = new ArrayList<>();

    private final List<String> popQuestions = new LinkedList<>();
    private final List<String> scienceQuestions = new LinkedList<>();
    private final List<String> sportsQuestions = new LinkedList<>();
    private final List<String> rockQuestions = new LinkedList<>();

    private int currentPlayerNumber = 0;

    private boolean isGettingOutOfPenaltyBox;
    private Player currentPlayer;

    public Game() {
        range(0, 50).forEach(i -> {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add(("Science Question " + i));
            sportsQuestions.add(("Sports Question " + i));
            rockQuestions.add("Rock Question " + i);
        });
    }

    public void add(String playerName) {
        addToPlayers(playerName);
        printPlayerAddedAndPlayerNumber(playerName);
    }

    private void printPlayerAddedAndPlayerNumber(String playerName) {
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    private void addToPlayers(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
        if (players.size() == 1) {
            currentPlayer = player;
        }
    }

    public void roll(int roll) {
        printCurrentPlayerName();
        printRoll(roll);

        if (currentPlayer.isInPenaltyBox()) {
            printIsPlayerGettingOutOfPenaltyBox(roll);
            setIsGettingOutOfPenaltyBox(roll);
            if (!isGettingOutOfPenaltyBox) return;
        }

        currentPlayer.move(roll);
        currentPlayer.printNewLocation();
        printCurrentCategory();
        askQuestion();
    }

    private void printCurrentCategory() {
        System.out.println("The category is " + currentCategory());
    }

    private void setIsGettingOutOfPenaltyBox(int roll) {
        isGettingOutOfPenaltyBox = isRollOdd(roll);
    }

    private void printIsPlayerGettingOutOfPenaltyBox(int roll) {
        if (isRollOdd(roll)) {
            System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
        } else {
            System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
        }
    }

    private boolean isRollOdd(int roll) {
        return roll % 2 != 0;
    }

    private void printRoll(int roll) {
        System.out.println("They have rolled a " + roll);
    }

    private void printCurrentPlayerName() {
        System.out.println(currentPlayer.getName() + " is the current player");
    }

    private void askQuestion() {
        switch (currentCategory()) {
            case POP:
                System.out.println(popQuestions.remove(0));
                break;
            case SCIENCE:
                System.out.println(scienceQuestions.remove(0));
                break;
            case SPORTS:
                System.out.println(sportsQuestions.remove(0));
                break;
            case ROCK:
                System.out.println(rockQuestions.remove(0));
                break;
        }
    }

    private QuestionCategory currentCategory() {
        return Arrays.stream(QuestionCategory.values())
                .filter(this::questionCategoryBelongingToCurrentPlace)
                .findFirst()
                .orElse(QuestionCategory.ROCK);
    }

    private boolean questionCategoryBelongingToCurrentPlace(QuestionCategory p) {
        return p.getPlace() == currentPlayer.getPlace() % 4;
    }

    public boolean wasCorrectlyAnswered() {
        if (!currentPlayer.isInPenaltyBox() || isGettingOutOfPenaltyBox) {
            printAnswerCorrect();
            currentPlayer.addCoin();
            currentPlayer.printCoins();
        }
        boolean winner = didPlayerWin();
        nextPlayer();
        return winner;
    }

    private void printAnswerCorrect() {
        System.out.println("Answer was correct!!!!");
    }

    private void nextPlayer() {
        currentPlayerNumber++;
        if (currentPlayerNumber == players.size()) {
            currentPlayerNumber = 0;
        }
        currentPlayer = players.get(currentPlayerNumber);
    }

    public boolean wrongAnswer() {
        printAnswerIncorrect();
        currentPlayer.putInPenaltyBox();
        printPlayerSentToPenaltyBox();

        nextPlayer();
        return true;
    }

    private void printPlayerSentToPenaltyBox() {
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
    }

    private void printAnswerIncorrect() {
        System.out.println("Question was incorrectly answered");
    }


    public int howManyPlayers() {
        return players.size();
    }

    private boolean didPlayerWin() {
        return !(currentPlayer.getCoins() == 6);
    }

    public int getPopQuestionsSize() {
        return popQuestions.size();
    }

    public int getScienceQuestionsSize() {
        return scienceQuestions.size();
    }

    public int getSportsQuestionsSize() {
        return sportsQuestions.size();
    }

    public int getRockQuestionsSize() {
        return rockQuestions.size();
    }

    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }
}
