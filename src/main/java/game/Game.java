package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

enum Categories {
    ZERO(0, "Pop"),
    ONE(1, "Science"),
    TWO(2, "Sports"),
    THREE(3, "Rock"),
    FOUR(4, "Pop"),
    FIVE(5, "Science"),
    SIX(6, "Sports"),
    SEVEN(7, "Rock"),
    EIGHT(8, "Pop"),
    NINE(9, "Science"),
    TEN(10, "Sports"),
    ELEVEN(11, "Rock");

    private final int place;
    private final String category;

    Categories(int place, String category) {
        this.place = place;
        this.category = category;
    }

    public String category() {
        return category;
    }

    public static Categories get(int aPlace) throws IllegalArgumentException {
        for (Categories category : Categories.values()) {
            if (category.place == aPlace)
                return category;
        }
        throw new IllegalArgumentException("Unknown category at place " + aPlace);
    }
}

class Questions {
    private static final int MAX_QUESTIONS_PER_CATEGORY = 50;

    private final LinkedList<String> popQuestions = new LinkedList<String>();
    private final LinkedList<String> scienceQuestions = new LinkedList<String>();
    private final LinkedList<String> sportsQuestions = new LinkedList<String>();
    private final LinkedList<String> rockQuestions = new LinkedList<String>();

    public Questions() {
        for (int i = 0; i < MAX_QUESTIONS_PER_CATEGORY; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public LinkedList<String> getPopQuestions() {
        return popQuestions;
    }

    public LinkedList<String> getScienceQuestions() {
        return scienceQuestions;
    }

    public LinkedList<String> getSportsQuestions() {
        return sportsQuestions;
    }

    public LinkedList<String> getRockQuestions() {
        return rockQuestions;
    }

    String askQuestionAt(int place) {
        switch (Categories.get(place).category()) {
            case "Pop":
                return popQuestions.removeFirst();
            case "Science":
                return scienceQuestions.removeFirst();
            case "Sports":
                return sportsQuestions.removeFirst();
            default:
                return rockQuestions.removeFirst();
        }
    }
}

public class Game {
    private static final int MAX_PLACES = 6;
    private static final int MAX_PURSES = 6;
    private static final int MAX_PENALTY_BOX = 6;

    private final int[] places = new int[MAX_PLACES];
    private final int[] purses = new int[MAX_PURSES];
    private final boolean[] inPenaltyBox = new boolean[MAX_PENALTY_BOX];

    private final ArrayList<String> players = new ArrayList<String>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    private int roll;

    private final Questions questions;

    public Game(String... players) {
        Collections.addAll(this.players, players);
        this.questions = new Questions();
        startTheGame();
    }

    private void startTheGame() {
        places[countPlayers()] = 0;
        purses[countPlayers()] = 0;
        inPenaltyBox[countPlayers()] = false;
    }

    public int countPlayers() {
        return players.size();
    }

    public void roll(int aRoll) {
        roll = aRoll;
        if (playerCanPlay()) {
            movePlayerOntoTheBoard();
            questions.askQuestionAt(places[currentPlayer]);
        }
    }

    private boolean isPlayerInPenaltyBox() {
        return inPenaltyBox[currentPlayer];
    }

    private boolean canPlayerGetOutOfPenaltyBox() {
        isGettingOutOfPenaltyBox = roll % 2 != 0;
        return isGettingOutOfPenaltyBox;
    }

    private void movePlayerOntoTheBoard() {
        places[currentPlayer] += roll;
        if (places[currentPlayer] > 11) {
            places[currentPlayer] -= 12;
        }
    }

    public boolean wasCorrectlyAnswered() {
        addPurse();
        nextPlayer();
        return didPlayerWin();
    }

    private void addPurse() {
        if (playerCanPlay()) {
            purses[currentPlayer]++;
        }
    }

    private boolean playerCanPlay() {
        return !isPlayerInPenaltyBox() || canPlayerGetOutOfPenaltyBox();
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    public boolean wrongAnswer() {
        movePlayerToPenaltyBox();
        nextPlayer();
        return true;
    }

    private void movePlayerToPenaltyBox() {
        inPenaltyBox[currentPlayer] = true;
    }

    private boolean didPlayerWin() {
        return (purses[currentPlayer] != 6);
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", places=" + Arrays.toString(places) +
                ", purses=" + Arrays.toString(purses) +
                ", inPenaltyBox=" + Arrays.toString(inPenaltyBox) +
                ", popQuestions=" + questions.getPopQuestions() +
                ", scienceQuestions=" + questions.getScienceQuestions() +
                ", sportsQuestions=" + questions.getSportsQuestions() +
                ", rockQuestions=" + questions.getRockQuestions() +
                ", currentPlayer=" + currentPlayer +
                ", isGettingOutOfPenaltyBox=" + isGettingOutOfPenaltyBox +
                '}';
    }
}