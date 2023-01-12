package game;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by ben on 14-2-19.
 */
class QuestionMaker {

    private final LinkedList popQuestions = new LinkedList();
    private final LinkedList scienceQuestions = new LinkedList();
    private final LinkedList sportsQuestions = new LinkedList();
    private final LinkedList rockQuestions = new LinkedList();

    public QuestionMaker() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public void askQuestion(Player currentPlayer) {
        if (currentCategory(currentPlayer) == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory(currentPlayer) == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory(currentPlayer) == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory(currentPlayer) == "Rock")
            System.out.println(rockQuestions.removeFirst());

    }

    public String currentCategory(Player currentPlayer) {
        if (currentPlayer.getPlace() == 0) return "Pop";
        if (currentPlayer.getPlace() == 8) return "Pop";
        if (currentPlayer.getPlace() == 1) return "Science";
        if (currentPlayer.getPlace() == 5) return "Science";
        if (currentPlayer.getPlace() == 9) return "Science";
        if (currentPlayer.getPlace() == 2) return "Sports";
        if (currentPlayer.getPlace() == 6) return "Sports";
        if (currentPlayer.getPlace() == 10) return "Sports";
        return "Rock";
    }
}

/**
 * Created by ben on 14-2-19.
 */
class Player {
    private final String name;
    private int place = 0;
    private int numberOfGoldCoins = 0;
    private boolean inPenaltyBox = false;

    public Player(String playerName) {
        this.name = playerName;
    }

    public int getPlace() {
        return place;
    }

    public void moveForward(int roll) {
        place += roll;
        if (place > 11) place -= 12;
    }

    public void winGoldCoin() {
        numberOfGoldCoins++;
    }

    public int getNumberOfGoldCoins() {
        return numberOfGoldCoins;
    }

    public boolean isInPenaltyBox() {
        return this.inPenaltyBox;
    }

    public void sendToPenaltyBox() {
        this.inPenaltyBox = true;
    }

    public String getPlayerName() {
        return this.name;
    }
}

public class Game {
    private final ArrayList<Player> players = new ArrayList<Player>();

    private int currentPlayerIndex = 0;
    private boolean isGettingOutOfPenaltyBox;
    private final QuestionMaker questionMaker = new QuestionMaker();

    public void add(String playerName) {
        players.add(new Player(playerName));

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    /**
     * Choose one of following as the next step of the current player according to the dice rolling number.
     * 1) moving forward and being asked a question
     * 2) getting out of the penalty box, moving forward and being asked a question
     * 3) staying in the penalty box
     *
     * @param rollingNumber
     */
    public void roll(int rollingNumber) {
        System.out.println(players.get(currentPlayerIndex).getPlayerName() + " is the current player");
        System.out.println("They have rolled a " + rollingNumber);

        if (!players.get(currentPlayerIndex).isInPenaltyBox()) {
            playerMoveForwardAndBeAskedQuestion(rollingNumber);
            return;
        }

        if (rollingNumber % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayerIndex).getPlayerName() + " is getting out of the penalty box");
            playerMoveForwardAndBeAskedQuestion(rollingNumber);
            return;
        }

        System.out.println(players.get(currentPlayerIndex).getPlayerName() + " is not getting out of the penalty box");
        isGettingOutOfPenaltyBox = false;
    }

    private void playerMoveForwardAndBeAskedQuestion(int roll) {
        players.get(currentPlayerIndex).moveForward(roll);

        System.out.println(players.get(currentPlayerIndex).getPlayerName()
                + "'s new location is "
                + players.get(currentPlayerIndex).getPlace());
        System.out.println("The category is " + questionMaker.currentCategory(players.get(currentPlayerIndex)));
        questionMaker.askQuestion(players.get(currentPlayerIndex));
    }

    /**
     * Present a gold coin to the current player who answers the question correctly,
     * find next player, and determine if the game will continue.
     *
     * @return
     */
    public boolean wasCorrectlyAnswered() {
        if (!players.get(currentPlayerIndex).isInPenaltyBox()) {
            return winGoldCoinAndFindNextPlayer();
        }

        if (isGettingOutOfPenaltyBox) {
            return winGoldCoinAndFindNextPlayer();
        }

        nextPlayer();
        return true;
    }

    private boolean winGoldCoinAndFindNextPlayer() {
        System.out.println("Answer was correct!!!!");
        players.get(currentPlayerIndex).winGoldCoin();
        System.out.println(players.get(currentPlayerIndex).getPlayerName()
                + " now has "
                + players.get(currentPlayerIndex).getNumberOfGoldCoins()
                + " Gold Coins.");

        boolean willContinue = willGameContinue();
        nextPlayer();

        return willContinue;
    }

    /**
     * Send the current play to the penalty box who answers the question wrongly, find
     * next player, and determine if the game will continue.
     *
     * @return
     */
    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayerIndex).getPlayerName() + " was sent to the penalty box");
        players.get(currentPlayerIndex).sendToPenaltyBox();

        nextPlayer();
        return true;
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
    }


    private boolean willGameContinue() {
        return !(players.get(currentPlayerIndex).getNumberOfGoldCoins() == 6);
    }
}