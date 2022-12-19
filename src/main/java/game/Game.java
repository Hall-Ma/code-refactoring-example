package game;

import java.util.ArrayList;
import java.util.LinkedList;

enum Category {
    POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock");
    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}

public class Game {
    private static final int NUMBER_OF_GAME_FIELDS = 12;
    private static final int COINS_NEEDED_TO_WIN = 6;
    ArrayList<String> players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];
    LinkedList<String> popQuestions = new LinkedList();
    LinkedList<String> scienceQuestions = new LinkedList();
    LinkedList<String> sportsQuestions = new LinkedList();
    LinkedList<String> rockQuestions = new LinkedList();
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game(){
        generateQuestionsByCategory();
    }

    private void generateQuestionsByCategory() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast(Category.POP + " Question " + i);
            scienceQuestions.addLast((Category.SCIENCE + " Question " + i));
            sportsQuestions.addLast((Category.SPORTS + " Question " + i));
            rockQuestions.addLast(Category.ROCK + " Question " + i);
        }
    }

    public void add(String playerName) {
        players.add(playerName);
        places[players.size()] = 0;
        purses[players.size()] = 0;
        inPenaltyBox[players.size()] = false;
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);
        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - NUMBER_OF_GAME_FIELDS;
                System.out.println(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - NUMBER_OF_GAME_FIELDS;
            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }
    }

    private void askQuestion() {
        String question;
        if (currentCategory() == Category.POP) {
            question = getFirstQuestionFromList(popQuestions);
            System.out.println(question);
        }
        if (currentCategory() == Category.SCIENCE) {
            question = getFirstQuestionFromList(scienceQuestions);
            System.out.println(question);
        }
        if (currentCategory() == Category.SPORTS) {
            question = getFirstQuestionFromList(sportsQuestions);
            System.out.println(question);
        }
        if (currentCategory() == Category.ROCK) {
            question = getFirstQuestionFromList(rockQuestions);
            System.out.println(question);
        }
    }

    private String getFirstQuestionFromList(LinkedList<String> popQuestions) {
        return popQuestions.removeFirst();
    }

    private Category currentCategory() {
        if (places[currentPlayer] == 0) return Category.POP;
        if (places[currentPlayer] == 4) return Category.POP;
        if (places[currentPlayer] == 8) return Category.POP;
        if (places[currentPlayer] == 1) return Category.SCIENCE;
        if (places[currentPlayer] == 5) return Category.SCIENCE;
        if (places[currentPlayer] == 9) return Category.SCIENCE;
        if (places[currentPlayer] == 2) return Category.SPORTS;
        if (places[currentPlayer] == 6) return Category.SPORTS;
        if (places[currentPlayer] == 10) return Category.SPORTS;
        return Category.ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                System.out.println(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");
                boolean winner = didPlayerWin();
                selectNextPlayerInTurn();
                return winner;
            } else {
                selectNextPlayerInTurn();
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");
            boolean winner = didPlayerWin();
            selectNextPlayerInTurn();
            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;
        selectNextPlayerInTurn();
        return true;
    }

    private void selectNextPlayerInTurn() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == COINS_NEEDED_TO_WIN);
    }

}
